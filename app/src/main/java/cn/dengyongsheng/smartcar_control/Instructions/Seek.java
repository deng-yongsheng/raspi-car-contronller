package cn.dengyongsheng.smartcar_control.Instructions;

import org.json.JSONObject;

/**
 * 查询小车状态的指令
 */
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
