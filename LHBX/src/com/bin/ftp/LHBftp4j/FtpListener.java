package com.bin.ftp.LHBftp4j;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;


public class FtpListener implements FTPDataTransferListener { 
        private FTPOptType optType; 
        //�ļ���ʼ�ϴ�������ʱ����
        private Boolean start=false;
        //��ʾ�Ѿ�������ֽ���
        private Boolean transferr=false;
        //�ļ��������ʱ������
        private Boolean complete=false;
        //�������ʱ����
        private Boolean aborte=false;
        //����ʧ��ʱ����
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
