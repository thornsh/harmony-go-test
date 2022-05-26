package com.sharedream.demo.harmony.bean;

public class GoServiceParam {
  private String bundleName;
  private String abilityName;
  private String key;

  public String getKey() {
    return key;
  }

  public String getBundleName() {
    return bundleName;
  }

  public String getAbilityName() {
    return abilityName;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setAbilityName(String abilityName) {
    this.abilityName = abilityName;
  }

  public void setBundleName(String bundleName) {
    this.bundleName = bundleName;
  }
}
