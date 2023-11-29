package study.cafekiosk.unit.beverage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    public void getName() throws Exception {
        // given
        Americano americano = new Americano();

        assertThat(americano.getName()).isEqualTo("아메리카노");
        // when

        // then
    }
    @Test
    public void getPrice() throws Exception {
        // given
        Americano americano = new Americano();
        assertThat(americano.getPrice()).isEqualTo(4000);
        // when
        // then
    }
}