package com.sharedream.demo.harmony;

import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExampleOhosTest {
  @Test
  public void testBundleName() {
    final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
    assertEquals("com.sharedream.demo.harmony.hmservice", actualBundleName);
  }
}