package core.Testing;

import core.Utils;
import core.economy;
import core.good;
import core.recipe;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class testing {
    economy temp1 = new economy(123123, 1);
    @Test
    public void roundTest() {
        assertThat(Utils.round(1.8, 0.25)).isEqualTo(1.75);
        assertThat(Utils.round(2.01, 1)).isEqualTo(2);
        assertThat(Utils.round(1.8, 0.2)).isEqualTo(1.8);
        assertThat(Utils.round(2.51, 0.25)).isEqualTo(2.5);
        assertThat(Utils.round(0, 0)).isEqualTo(0);

        assertThat(Utils.round(1.8, 1)).isEqualTo(1.8);
        assertThat(Utils.round(2.01, 2.01)).isEqualTo(2);
        assertThat(Utils.round(1.8, 3)).isEqualTo(1.8);
        assertThat(Utils.round(2.51561, 4)).isEqualTo(2.5156);
        assertThat(Utils.round(0, 5)).isEqualTo(0);
    }
    @Test
    public void randRecipeTest() {
        for (int i = 0; i < 100; i++) {
            recipe temp = good.randRecipe("Copper", true);
            boolean contains = false;
            for (String x: temp.getInputName()) {
                contains = x.equals("Copper");
                if (contains) {
                    break;
                }
            }
            for (String x: temp.getOutputName()) {
                contains = !x.equals("Copper");
                if (!contains) {
                    break;
                }
            }
            assertThat(contains).isTrue();
        }
    }

    @Test
    public void primaryRecipeWithGoodTest() {
        recipe zinc = new recipe(new good[]{}, new good[]{new good("Zinc", 1)}, 3, 0);
        recipe copper = new recipe(new good[]{}, new good[]{new good("Copper", 1)}, 3, 0);
        recipe brass = new recipe(new good[]{new good("Zinc", 1), new good("Copper", 2)}, new good[]{new good("Brass", 1)}, 3, 0);

        recipe[] check = good.primaryRecipeWithGood("Copper");
        assertThat(check).asList().contains(copper);
        check = good.primaryRecipeWithGood("Zinc");
        assertThat(check).asList().contains(zinc);
        check = good.primaryRecipeWithGood("Brass");
        assertThat(check).asList().containsAnyIn(new recipe[]{copper, zinc, brass});
    }
}
