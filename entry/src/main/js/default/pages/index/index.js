import { openService } from '../../../utils/callAbilityFromService.js'

export default {
  data: {
    title: ""
  },
  onInit() {
    this.title = this.$t('strings.world');
  },
  async click() {
    console.log(88888)
    try {
      console.log(99999)
      await openService("com.sharedream.jikeselected.harmony", "com.sharedream.jikeselected.harmony.MainAbility", "key")
    } catch (e) {
      console.log(e)
      console.log(22222)
    }
  }
}
