package core.Testing;

import core.Utils;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class testing {
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


}
