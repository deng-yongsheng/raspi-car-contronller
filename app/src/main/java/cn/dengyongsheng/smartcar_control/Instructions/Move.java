package cn.dengyongsheng.smartcar_control.Instructions;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 小车行进指令
 */
public class Move extends InstructionBase {
    public float speed;
    public float direction;

    /**
     * 构造移动指令对象
     *
     * @param speed     速度
     * @param direction 方向
     */
    public Move(float speed, float direction) {
        this.speed = speed;
        this.direction = direction;
    }

    @Override
    public String getInstruction() {
        return "MOVE";
    }

    @Override
    public JSONObject getContent() {
        return new JSONObject(new HashMap<String, Float>() {{
            put("speed", speed);
            put("direction", direction);
        }});
    }
}
