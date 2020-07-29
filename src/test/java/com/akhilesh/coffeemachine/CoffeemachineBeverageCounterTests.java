package com.akhilesh.coffeemachine;

import com.akhilesh.coffeemachine.model.BeverageCounters;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CoffeemachineApplicationTests.class})
public class CoffeemachineBeverageCounterTests {

    /**
     * Try to set +ve, -ve and 0 value
     * to beverage counter
     */
    @Test
    public void testSettingOfBeverageCounters(){
        BeverageCounters.setNumberOfCounters(5);
        assertEquals(5,BeverageCounters.getNumberOfCounters());

        assertThrows(IllegalArgumentException.class, () -> {
            BeverageCounters.setNumberOfCounters(-5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            BeverageCounters.setNumberOfCounters(0);
        });
    }
}
