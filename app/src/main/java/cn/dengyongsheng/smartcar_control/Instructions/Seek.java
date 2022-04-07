package cn.dengyongsheng.smartcar_control.Instructions;

import org.json.JSONObject;

public class Seek extends InstructionBase {
    @Override
    public String getInstruction() {
        return "SEEK";
    }

    @Override
    public JSONObject getContent() {
        return null;
    }
}
