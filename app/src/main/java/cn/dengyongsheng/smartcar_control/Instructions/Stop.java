package cn.dengyongsheng.smartcar_control.Instructions;

import org.json.JSONObject;

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
