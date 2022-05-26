package com.sharedream.demo.harmony;

import com.sharedream.demo.harmony.bean.*;
import com.sharedream.demo.harmony.utils.PreferenceUtils;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.app.Context;
import ohos.rpc.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.IntentConstants;
import ohos.utils.net.Uri;
import ohos.utils.zson.ZSONObject;

import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MServiceAbility extends Ability {
  private MServiceAbility.TargetRemote targetRemote;
  @Override
  protected IRemoteObject onConnect(Intent intent) {
    Context context = getContext();
    targetRemote = new MServiceAbility.TargetRemote(context);
    return targetRemote.asObject();
  }

  class TargetRemote extends RemoteObject implements IRemoteBroker {
    private Context context;
    private Socket socket = null;
    private OutputStream outputStream = null;

    private static final int SUCCESS = 0;
    private static final int ERROR = 1;

    private static final int TEST = 1001;
    private static final int GETSTORE = 1002;
    private static final int SETSTORE = 1003;
    private static final int OPEN = 1004;
    private static final int PHONE = 1005;
    private static final int TOSERVICE = 1006;

    public TargetRemote(Context context) {
      super("TargetRemote");
      this.context = context;
    }

    @Override
    public boolean onRemoteRequest(int code, MessageParcel data, MessageParcel reply, MessageOption option) throws RemoteException {
      switch (code) {
        case TEST: {
          String dataStr = data.readString();
          TestParam param = new TestParam();

          try {
            param = ZSONObject.stringToClass(dataStr, TestParam.class);
          } catch (RuntimeException e) {
            System.out.println("error=================");
          }

          Map<String, Object> result = new HashMap<String, Object>();
          result.put("code", SUCCESS);
          result.put("abilityResult", param.getFirstNum() + param.getSecondNum());
          reply.writeString(ZSONObject.toZSONString(result));
          break;
        }
        case GETSTORE: {
          String dataStr = data.readString();
          GetStoreParam getStoreParam = new GetStoreParam();
          String key;
          String defaultValue;
          StoreParam storeParam = new StoreParam();
          String storeValue;

          try {
            getStoreParam = ZSONObject.stringToClass(dataStr, GetStoreParam.class);
            key = getStoreParam.getKey();
            defaultValue = getStoreParam.getDefaultValue();
            storeValue = PreferenceUtils.getInstance().getString(key, defaultValue);
            storeParam.setKey(key);
            storeParam.setValue(storeValue);
          } catch (RuntimeException e) {

          }

          Map<String, Object> result = new HashMap<String, Object>();
          result.put("code", SUCCESS);
          result.put("abilityResult", ZSONObject.toZSONString(storeParam));
          reply.writeString(ZSONObject.toZSONString(result));
          break;
        }
        case SETSTORE: {
          String dataStr = data.readString();
          StoreParam storeParam = new StoreParam();
          String key;
          String value;

          try {
            storeParam = ZSONObject.stringToClass(dataStr, StoreParam.class);
            key = storeParam.getKey();
            value = storeParam.getValue();
            PreferenceUtils.getInstance().putString(key, value);
          } catch (Exception e) {
            System.out.println("设置store失败========");
          }

          Map<String, Object> result = new HashMap<String, Object>();
          result.put("code", SUCCESS);
          result.put("abilityResult", true);
          reply.writeString(ZSONObject.toZSONString(result));
          break;
        }
        case OPEN: {
          String dataStr = data.readString();
          OpenParam openParam = new OpenParam();
          String url;
          String value;

          try {
            openParam = ZSONObject.stringToClass(dataStr, OpenParam.class);
            url = openParam.getUrl();
            value = openParam.getValue();

            Intent intent = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withBundleName(getBundleName())
                    .withAbilityName(url)
                    .build();
            intent.setOperation(operation);
            if (value != null) {
              intent.setParam("value", value);
            }

            startAbility(intent);
          } catch (Exception e) {
            System.out.println("跳转ability失败========");
          }
          break;
        }
        case TOSERVICE: {
          String dataStr = data.readString();
          GoServiceParam param = new GoServiceParam();
          String bundleName;
          String abilityName;
          String key;

          try {
            System.out.println("33333=========");
            param = ZSONObject.stringToClass(dataStr, GoServiceParam.class);
            bundleName = param.getBundleName();
            abilityName = param.getAbilityName();
            key = param.getKey();

            Intent intent = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withBundleName(bundleName)
                    .withAbilityName(abilityName)
                    .build();
            intent.setOperation(operation);
            intent.setParam("key", key);
            System.out.println("111222=========");

            startAbility(intent);
          } catch (Exception e) {
            System.out.println("跳转service失败=========");
          }

          break;
        }
        case PHONE: {
          String dataStr = data.readString();
          RequestParam param = new RequestParam();
          param = ZSONObject.stringToClass(dataStr, RequestParam.class);

          Intent intent = new Intent();
          Operation operation = new Intent.OperationBuilder()
                  .withUri(Uri.parse("tel:" + param.getValue()))
                  .withAction(IntentConstants.ACTION_DIAL)
                  .build();
          intent.setOperation(operation);

          startAbility(intent);
          break;
        }
        default: {
          Map<String, Object> result = new HashMap<String, Object>();
          result.put("abilityError", ERROR);
          reply.writeString(ZSONObject.toZSONString(result));
          return false;
        }
      }
      return true;
    }

    @Override
    public IRemoteObject asObject() {
      return this;
    }
  }
}
