package enums;

public enum CategoryEnums {
    SUCCESS(1,"操作成功"),
    EXIST(0,"目录名字已存在"),
    NOTEXIST(-5,"该目录不存在"),
    NAMENULL(-1,"目录名字为空"),
    FAIL(-2,"操作失败"),
    FRESHFAIL(-4,"刷新id失败"),
    UNOPERATION(-3,"无权限"),
    INNER_ERROR(-1,"系统异常"),
    UNLOGIN(-10,"未登录"),
    ;


        private int state;

        private String stateInfo;

        public static CategoryEnums stateof(int index) {
            for (enums.CategoryEnums state : values()) {
                if (state.getState() == index)
                    return state;
            }
            return null;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateInfo() {
            return stateInfo;
        }

        public void setStateInfo(String stateInfo) {
            this.stateInfo = stateInfo;
        }

        CategoryEnums(int state, String stateInfo) {
            this.state = state;
            this.stateInfo = stateInfo;
        }
}


