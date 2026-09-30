package com.waterycontinent.bitsandbobs.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.Optional;

public record BeverageRecipe(Ingredient firstIngredient, Ingredient secondIngredient, Ingredient thirdIngredient, Ingredient container,
                             Optional<SizedFluidIngredient> firstFluid, Optional<SizedFluidIngredient> secondFluid,
                             String temperature, ItemStack result, int processingTime) implements Recipe<BeverageRecipeInput> {
    public static final RecipeType<BeverageRecipe> TYPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath("bitsandbobs", "beverage_making"));

    public static final MapCodec<BeverageRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("first_ingredient").forGetter(BeverageRecipe::firstIngredient),
            Ingredient.CODEC.optionalFieldOf("second_ingredient", Ingredient.EMPTY).forGetter(BeverageRecipe::secondIngredient),
            Ingredient.CODEC.optionalFieldOf("third_ingredient", Ingredient.EMPTY).forGetter(BeverageRecipe::thirdIngredient),
            Ingredient.CODEC_NONEMPTY.fieldOf("container").forGetter(BeverageRecipe::container),
            SizedFluidIngredient.FLAT_CODEC.optionalFieldOf("first_fluid").forGetter(BeverageRecipe::firstFluid),
            SizedFluidIngredient.FLAT_CODEC.optionalFieldOf("second_fluid").forGetter(BeverageRecipe::secondFluid),
            com.mojang.serialization.Codec.STRING.validate(value -> value.equals("hot") || value.equals("cold")
                    ? com.mojang.serialization.DataResult.success(value)
                    : com.mojang.serialization.DataResult.error(() -> "temperature must be 'hot' or 'cold'"))
                    .fieldOf("temperature").forGetter(BeverageRecipe::temperature),
            ItemStack.CODEC.fieldOf("result").forGetter(BeverageRecipe::result),
            com.mojang.serialization.Codec.intRange(1, 32767).optionalFieldOf("processing_time", 100).forGetter(BeverageRecipe::processingTime)
            ).apply(instance, BeverageRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BeverageRecipe> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public BeverageRecipe decode(RegistryFriendlyByteBuf buffer) {
            return new BeverageRecipe(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer),
                    Ingredient.CONTENTS_STREAM_CODEC.decode(buffer), Ingredient.CONTENTS_STREAM_CODEC.decode(buffer),
                    Ingredient.CONTENTS_STREAM_CODEC.decode(buffer),
                    ByteBufCodecs.optional(SizedFluidIngredient.STREAM_CODEC).decode(buffer), ByteBufCodecs.optional(SizedFluidIngredient.STREAM_CODEC).decode(buffer),
                    ByteBufCodecs.STRING_UTF8.decode(buffer), ItemStack.STREAM_CODEC.decode(buffer), ByteBufCodecs.VAR_INT.decode(buffer));
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buffer, BeverageRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.firstIngredient());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.secondIngredient());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.thirdIngredient());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.container());
            ByteBufCodecs.optional(SizedFluidIngredient.STREAM_CODEC).encode(buffer, recipe.firstFluid());
            ByteBufCodecs.optional(SizedFluidIngredient.STREAM_CODEC).encode(buffer, recipe.secondFluid());
            ByteBufCodecs.STRING_UTF8.encode(buffer, recipe.temperature());
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result());
            ByteBufCodecs.VAR_INT.encode(buffer, recipe.processingTime());
        }
    };

    public boolean matches(BeverageRecipeInput input, Level level) {
        if (!container.test(input.container())) return false;

        // Try all six orders so overlapping ingredients also get a valid assignment.
        for (int first = 0; first < 3; first++) {
            for (int second = 0; second < 3; second++) {
                if (first == second) continue;
                int third = 3 - first - second;
                if (matchesIngredient(firstIngredient, input.getItem(first))
                        && matchesIngredient(secondIngredient, input.getItem(second))
                        && matchesIngredient(thirdIngredient, input.getItem(third))) return true;
            }
        }
        return false;
    }

    private static boolean matchesIngredient(Ingredient ingredient, ItemStack stack) {
        return ingredient.isEmpty() ? stack.isEmpty() : ingredient.test(stack);
    }

    public ItemStack assemble(BeverageRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean isSpecial() { return true; }

    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(4, Ingredient.EMPTY);
        ingredients.set(0, firstIngredient);
        ingredients.set(1, secondIngredient);
        ingredients.set(2, thirdIngredient);
        ingredients.set(3, container);
        return ingredients;
    }

    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BEVERAGE_SERIALIZER.get();
    }

    public RecipeType<?> getType() {
        return TYPE;
    }
}
