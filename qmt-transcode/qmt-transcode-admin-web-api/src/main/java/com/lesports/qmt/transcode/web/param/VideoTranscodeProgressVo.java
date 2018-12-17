package com.lesports.qmt.transcode.web.param;

import java.util.List;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/10/28
 */
public class VideoTranscodeProgressVo {
    private List<FormatInfo> formatInfos;
    private Integer errCode;
    private String errMsg;
    private Integer checkCode;
    private String checkMsg;

    public Integer getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(Integer checkCode) {
        this.checkCode = checkCode;
    }

    public String getCheckMsg() {
        return checkMsg;
    }

    public void setCheckMsg(String checkMsg) {
        this.checkMsg = checkMsg;
    }

    public static class FormatInfo {
        private String formatName;
        private Integer errCode;
        private String errMsg;
        private String progress;
        private String vtype;
        private Integer checkCode;
        private String checkMsg;

        public String getFormatName() {
            return formatName;
        }

        public void setFormatName(String formatName) {
            this.formatName = formatName;
        }

        public Integer getErrCode() {
            return errCode;
        }

        public void setErrCode(Integer errCode) {
            this.errCode = errCode;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public String getVtype() {
            return vtype;
        }

        public void setVtype(String vtype) {
            this.vtype = vtype;
        }

        public Integer getCheckCode() {
            return checkCode;
        }

        public void setCheckCode(Integer checkCode) {
            this.checkCode = checkCode;
        }

        public String getCheckMsg() {
            return checkMsg;
        }

        public void setCheckMsg(String checkMsg) {
            this.checkMsg = checkMsg;
        }
    }

    public List<FormatInfo> getFormatInfos() {
        return formatInfos;
    }

    public void setFormatInfos(List<FormatInfo> formatInfos) {
        this.formatInfos = formatInfos;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
