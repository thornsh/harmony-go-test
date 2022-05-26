const ABILITY_TYPE_EXTERNAL = 0;

const ACTION_SYNC = 0;

const ACTION_MESSAGE_CODE_TEST = 1001;
const ACTION_MESSAGE_CODE_GETSTORE = 1002;
const ACTION_MESSAGE_CODE_SETSTORE = 1003;
const ACTION_MESSAGE_CODE_OPEN = 1004;
const ACTION_MESSAGE_CODE_PHONE = 1005;
const ACTION_MESSAGE_CODE_GOSERVICE = 1006;

function createAction(code, data) {
  const action = {};
  action.bundleName = 'com.sharedream.demo.harmony';
  action.abilityName = 'com.sharedream.demo.harmony.MServiceAbility';
  action.abilityType = ABILITY_TYPE_EXTERNAL;
  action.syncOption = ACTION_SYNC;
  action.messageCode = code;
  action.data = data;

  return action
}

async function test() {
  const actionData = {};
  actionData.firstNum = 1024;
  actionData.secondNum = 2048;

  const action = createAction(ACTION_MESSAGE_CODE_TEST, actionData)

  const result = await FeatureAbility.callAbility(action);
  const res = JSON.parse(result);
  if (res.code === 0) {
    console.info(`test success${res.abilityResult}===============`);
  } else {
    console.error('error', 'error============')
  }
}

async function getStore(key, defaultValue) {
  const actionData = {
    key,
    defaultValue
  }

  const action = createAction(ACTION_MESSAGE_CODE_GETSTORE, actionData)

  const result = await FeatureAbility.callAbility(action)
  const res = JSON.parse(result)
  if (res.code === 0) {
    return {
      ...res,
      data: JSON.parse(res.abilityResult).value
    }
  } else {
    console.error('error', 'error============')
  }
}

async function setStore(key, value) {
  const actionData = {
    key,
    value
  }

  const action = createAction(ACTION_MESSAGE_CODE_SETSTORE, actionData)

  const result = await FeatureAbility.callAbility(action)
  const res = JSON.parse(result)
  if (res.code === 0) {
    return {
      ...res,
      data: res.abilityResult
    }
  } else {
    console.error('error', 'error============')
  }
}

async function openPage(url, value = '') {
  const actionData = {
    url,
    value
  }

  const action = createAction(ACTION_MESSAGE_CODE_OPEN, actionData)

  await FeatureAbility.callAbility(action)
}

async function openService(bundleName, abilityName, key) {
  const actionData = {
    bundleName,
    abilityName,
    key
  }

  const action = createAction(ACTION_MESSAGE_CODE_GOSERVICE, actionData)

  await FeatureAbility.callAbility(action)
}

async function goPhone(value) {
  const actionData = {
    value
  }

  const action = createAction(ACTION_MESSAGE_CODE_PHONE, actionData)

  await FeatureAbility.callAbility(action)
}

export {
  test,
  getStore,
  setStore,
  openPage,
  goPhone,
  openService
}
