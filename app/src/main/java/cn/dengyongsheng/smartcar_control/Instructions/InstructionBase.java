package cn.dengyongsheng.smartcar_control.Instructions;


import org.json.JSONException;
import org.json.JSONObject;

public abstract class InstructionBase {
    public float version = 1;
    public int seq;

    /**
     * 获取指令名称
     */
    public abstract String getInstruction();

    /**
     * 获取指令的补充内容
     */
    public abstract JSONObject getContent();

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("version", this.version);
            jsonObject.put("seq", this.seq);
            jsonObject.put("instruction", this.getInstruction());
            jsonObject.put("content", this.getContent());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
