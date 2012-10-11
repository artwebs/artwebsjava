package com.bin.ftp.LHBftp4j;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;


public class FtpListener implements FTPDataTransferListener { 
        private FTPOptType optType; 
        //文件开始上传或下载时触发
        private Boolean start=false;
        //显示已经传输的字节数
        private Boolean transferr=false;
        //文件传输完成时，触发
        private Boolean complete=false;
        //传输放弃时触发
        private Boolean aborte=false;
        //传输失败时触发
        private Boolean fail=false;
        private Integer length=0;

        public Integer getLength() {
			return length;
		}

		public FTPOptType getOptType() {
			return optType;
		}

		public Boolean getStart() {
			return start;
		}

		public Boolean getTransferr() {
			return transferr;
		}

		public Boolean getComplete() {
			return complete;
		}

		public Boolean getAborte() {
			return aborte;
		}

		public Boolean getFail() {
			return fail;
		}

		public static FtpListener instance(FTPOptType optType) { 
                return new FtpListener(optType); 
        } 

        private FtpListener(FTPOptType optType) { 
                this.optType = optType; 
        } 

        public void started() { 
                this.start=true;
        } 

        public void transferred(int length) { 
                this.transferr=true;
                this.length=length; 
        } 

        public void completed() { 
               this.complete=true;
        } 

        public void aborted() { 
               this.aborte=true;
        } 

        public void failed() { 
               this.fail=true;
        } 
}
