package study.cafekiosk.learning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GuavaLearningTest {
    @DisplayName("주어진 개수만큼 리스트를 파티셔닝한다.")
    @Test
    void partitionLearningTest1() {
        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 2);

        // then
        assertThat(partition).hasSize(3)
                .isEqualTo(List.of(
                        List.of(1, 2), List.of(3,4), List.of(5,6)
                ));
    }
    @DisplayName("멀티맵 기능 확인")
    @Test
    void multimapLearningTest2() {
        // given
        Multimap<String,String> multimaps = ArrayListMultimap.create();
        multimaps.put("커피", "아메리카노");
        multimaps.put("커피", "카페라떼");
        multimaps.put("커피", "카푸치노");
        multimaps.put("베이커리", "크루아상");
        multimaps.put("베이커리", "식빵");

        // when
        Collection<String> strings = multimaps.get("커피");

        // then
        assertThat(strings).hasSize(3)
                .isEqualTo(List.of("아메리카노", "카페라떼", "카푸치노"));

    }
    @DisplayName("멀티맵 삭제 기능 확인")
    @TestFactory
    Collection<DynamicTest> multimapLearningTest1() {
        // given
        Multimap<String,String> multimaps = ArrayListMultimap.create();
        multimaps.put("커피", "아메리카노");
        multimaps.put("커피", "카페라떼");
        multimaps.put("커피", "카푸치노");
        multimaps.put("베이커리", "크루아상");
        multimaps.put("베이커리", "식빵");


        // then
        return List.of(
                DynamicTest.dynamicTest("1개 value 삭제", () -> {
                    // when
                    multimaps.remove("커피", "카푸치노");
                    Collection<String> strings = multimaps.get("커피");

                    // then
                    assertThat(strings).hasSize(2)
                            .isEqualTo(List.of("아메리카노", "카페라떼"));
                }),
                DynamicTest.dynamicTest("1개 Key 삭제", () -> {
                    // when
                    multimaps.removeAll("커피");

                    // then
                    Collection<String> results = multimaps.get("커피");
                    assertThat(results).isEmpty();

                })
        );

    }
}
