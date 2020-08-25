package com.nucarf.base.bean;

import java.util.ArrayList;
import java.util.List;

public class CityBean {

    /**
     * value : 110000
     * text : 北京市
     * children : [{"value":"110101","text":"北京市","children":[{"value":"110101","text":"东城区"},{"value":"110102","text":"西城区"},{"value":"110103","text":"崇文区"},{"value":"110104","text":"宣武区"},{"value":"110105","text":"朝阳区"},{"value":"110106","text":"丰台区"},{"value":"110107","text":"石景山区"},{"value":"110108","text":"海淀区"},{"value":"110109","text":"门头沟区"},{"value":"110111","text":"房山区"},{"value":"110112","text":"通州区"},{"value":"110113","text":"顺义区"},{"value":"110114","text":"昌平区"},{"value":"110115","text":"大兴区"},{"value":"110116","text":"怀柔区"},{"value":"110117","text":"平谷区"},{"value":"110228","text":"密云县"},{"value":"110229","text":"延庆县"},{"value":"110230","text":"其它区"}]}]
     */

    private String value;
    private String text;
    private List<ChildrenBeanX> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ChildrenBeanX> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBeanX> children) {
        this.children = children;
    }

    public static class ChildrenBeanX {
        /**
         * value : 110101
         * text : 北京市
         * children : [{"value":"110101","text":"东城区"},{"value":"110102","text":"西城区"},{"value":"110103","text":"崇文区"},{"value":"110104","text":"宣武区"},{"value":"110105","text":"朝阳区"},{"value":"110106","text":"丰台区"},{"value":"110107","text":"石景山区"},{"value":"110108","text":"海淀区"},{"value":"110109","text":"门头沟区"},{"value":"110111","text":"房山区"},{"value":"110112","text":"通州区"},{"value":"110113","text":"顺义区"},{"value":"110114","text":"昌平区"},{"value":"110115","text":"大兴区"},{"value":"110116","text":"怀柔区"},{"value":"110117","text":"平谷区"},{"value":"110228","text":"密云县"},{"value":"110229","text":"延庆县"},{"value":"110230","text":"其它区"}]
         */

        private String value;
        private String text;
        private ArrayList<ChildrenBean> children;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public ArrayList<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * value : 110101
             * text : 东城区
             */

            private String value;
            private String text;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
