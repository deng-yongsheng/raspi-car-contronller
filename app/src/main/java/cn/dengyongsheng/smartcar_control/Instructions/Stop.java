package cn.dengyongsheng.smartcar_control.Instructions;

import org.json.JSONObject;

/**
 * 小车的停止指令
 */
public class Stop extends InstructionBase {
    @Override
    public String getInstruction() {
        return "STOP";
    }

    @Override
    public JSONObject getContent() {
        return null;
    }
}
