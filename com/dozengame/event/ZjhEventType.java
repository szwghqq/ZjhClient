package com.dozengame.event;

/**
 * �¼�����
 * 
 * @author hewengao
 * 
 */
public class ZjhEventType {
	 
	public static final String ON_TEX_RECV_RESETDISPLAY="ON_TEX_RECV_RESETDISPLAY";	//���µ�¼��ʾ���������������
	public static final String ON_TEX_RECV_TEX_READY = "ON_TEX_RECV_TEX_READY"; // �յ�׼��
	public static final String ON_TEX_RECV_FAPAI = "ON_TEX_RECV_FAPAI"; // �յ�����
	public static final String ON_TEX_RECV_BESTPOKES = "ON_TEX_RECV_BESTPOKES"; // �յ��������
	public static final String ON_TEX_RECV_MYPOKE = "ON_TEX_RECV_MYPOKE"; // �յ��ҵ��˿�
	public static final String ON_TEX_AUTO_PANEL = "ON_TEX_AUTO_PANEL"; // �Զ����
	public static final String ON_TEX_RECV_GIVEUP = "ON_TEX_RECV_GIVEUP"; // �յ�Ͷ������Ϣ
	public static final String ON_TEX_RECV_XIAZHUSUCC = "ON_TEX_RECV_XIAZHUSUCC"; // �յ���ע�ɹ�
	public static final String ON_TEX_RECV_BUXIAZHU = "ON_TEX_RECV_BUXIAZHU"; // �յ�����ע
	public static final String ON_TEX_BUTTON_STATUS = "ON_TEX_BUTTON_STATUS"; // ���ð�ť��Ӧ�Ķ���
	public static final String ON_TEX_RECV_GAMEOVER = "ON_TEX_RECV_GAMEOVER"; // �յ���Ϸ���������¿�ʼ
	public static final String ON_TEX_RECV_REFRESHGOLD = "ON_TEX_RECV_REFRESHGOLD"; // ����������λ�ϵĽ��
	public static final String ON_TEX_RECV_DESK_POLL_INFO = "ON_TEX_RECV_DESK_POLL_INFO"; // �յ�����ʳ���Ϣ
	public static final String ON_TEX_RECV_DESK_INFO = "ON_TEX_RECV_DESK_INFO"; // �յ�����Ϣ
	public static final String ON_TEX_RECV_SHOW_MY_TOTAL_BEAN = "ON_TEX_RECV_SHOW_MY_TOTAL_BEAN"; // �յ��ҵĵ��ݶ�
	public static final String ON_TEX_RECV_PLAYERINFO = "ON_TEX_RECV_PLAYERINFO"; // ����������״̬
	public static final String ON_TEX_RECV_POKEINFO = "ON_TEX_RECV_POKEINFO"; // ����ĳ����λ�ϵ���(�ص�½��)
	public static final String ON_TEX_RECV_RELOGIN_DESKGOLD = "ON_TEX_RECV_RELOGIN_DESKGOLD"; // �յ������Ҹı�(�ص�½��)
	public static final String ON_TEX_RECV_GAMESTART = "ON_TEX_RECV_GAMESTART"; // �յ���Ϸ��ʼ
	public static final String ON_TEX_RECV_BUYCHOUMA = "ON_TEX_RECV_BUYCHOUMA"; // �յ��������
	public static final String ON_TEX_RECV_JS_WAITING = "ON_TEX_RECV_JS_WAITING"; // �յ��������ڽ���

	public static final String ON_TEX_RECV_TIMEOUT = "ON_TEX_RECV_TIMEOUT"; // �յ�����
	public static final String ON_TEX_RECV_SITDOWN = "ON_TEX_RECV_SITDOWN"; // ����
	public static final String ON_TEX_RECV_STANDUP = "ON_TEX_RECV_STANDUP"; // վ��
	public static final String ON_TEX_RECV_SERVER_ERROR = "ON_TEX_RECV_SERVER_ERROR"; // �յ�����˵���ʾ��Ϣ
	public static final String ON_TEX_RECV_CHANGEFACE = "ON_TEX_RECV_CHANGEFACE"; // �յ�����޸�ͷ��

	public static final String ON_TEX_RECV_SPECAL_ICON = "ON_TEX_RECV_SPECAL_ICON"; // �յ������ʶ

	public static final String ON_TEX_RECV_DESKPOKE = "ON_TEX_RECV_DESKPOKE"; // �յ������˿�
	public static final String ON_TEX_RECV_WATCHING = "ON_TEX_RECV_WATCHING"; // �յ��û���ս
	public static final String ON_RECV_EXIT_WATCH = "ON_RECV_EXIT_WATCH"; // �յ��˳���ս
	public static final String ON_RECV_ADD_EXPERIENCE = "ON_RECV_ADD_EXPERIENCE"; // �յ����Ӿ���
	public static final String ON_RECV_LEVEL_UPGRADE = "ON_RECV_LEVEL_UPGRADE"; // �յ��û��ȼ�����
	public static final String ON_RECV_DAY_ADDEXP = "ON_RECV_DAY_ADDEXP"; // �յ��û���������;������
	public static final String ON_RECV_LEVEL_PRIZE_OR_LOST = "ON_RECV_LEVEL_PRIZE_OR_LOST"; // �յ��Լ��ý�����̭
	public static final String ON_RECV_LEVEL_SERVER_MSG = "ON_RECV_LEVEL_SERVER_MSG"; // �յ��û���������ʾ����Ϣ

	public static final String ON_BACK_HALL_RESULT = "ON_BACK_HALL_RESULT"; // �յ�������֪ͨ���ش���

	public static final String ON_TEX_RECV_GIFT_SHOP = "ON_TEX_RECV_GIFT_SHOP"; // �յ��̳������б�
	public static final String ON_TEX_RECV_GIFT_LIST = "ON_TEX_RECV_GIFT_LIST"; // �յ������б�
	public static final String ON_TEX_RECV_GIFT_ICON = "ON_TEX_RECV_GIFT_ICON"; // �յ������ʶ
	public static final String ON_TEX_RECV_PLAY_GIFT = "ON_TEX_RECV_PLAY_GIFT"; // ���������ﶯ��
	public static final String ON_TEX_RECV_PLAY_EMOT = "ON_TEX_RECV_PLAY_EMOT"; // ���ű���
	public static final String ON_TEX_RECV_GIFT_FAILD = "ON_TEX_RECV_GIFT_FAILD"; // ����������ͱ���ʧ��
	public static final String ON_TEX_RECV_GIFT_FAILDLIST = "ON_TEX_RECV_GIFT_FAILDLIST"; // ������������ʧ��
	public static final String ON_TEX_RECV_GIFT_SALE = "ON_TEX_RECV_GIFT_SALE"; // �յ�����������״̬
	public static final String ON_RECV_GIFT_XINSHOU_ONE = "ON_RECV_GIFT_XINSHOU_ONE"; // ����һ�����Ƶ�����������
	// �յ�����ظ�
 
	public static final String ON_TEX_RECV_GIFT_RESPONSE = "ON_TEX_RECV_GIFT_RESPONSE";

	public static final String ON_RECV_LOGIN_SHOW_BETAGIFE = "ON_RECV_LOGIN_SHOW_BETAGIFE";
	public static final String ON_RECV_LOGIN_GET_BETAGIFE = "ON_RECV_LOGIN_GET_BETAGIFE";

	// m_gsProcessor.addEventListener("ON_TEX_RECV_BBS_URL",this,"");

	public static final String ON_TEX_RECV_SINGLE_DETAIL = "ON_TEX_RECV_SINGLE_DETAIL";

	public static final String ON_RECV_GIVE_JIUJI = "ON_RECV_GIVE_JIUJI";
	// ------����start-----------------------
	public static final String ON_RECV_KICK_ENABLE = "ON_RECV_KICK_ENABLE";
	public static final String ON_RECV_KICK = "ON_RECV_KICK";
	public static final String ON_RECV_KICK_RESULT = "ON_RECV_KICK_RESULT";
	public static final String ON_RECV_KICK_CISHU = "ON_RECV_KICK_CISHU";
	public static final String ON_RECV_KICK_OVER = "ON_RECV_KICK_OVER";
	public static final String ON_RECV_KICK_OVER_BEITI = "ON_RECV_KICK_OVER_BEITI";
	public static final String ON_RECV_KICK_SHOW = "ON_RECV_KICK_SHOW";
	// ------����end-----------------------

	// ----����start-----------------------
	public static final String ON_RECV_CALC_ENABLE = "ON_RECV_CALC_ENABLE";
	// ------����end-----------------------
	//����
	public static final String ON_TEX_RECV_CHAT =  "ON_TEX_RECV_CHAT";
	
	 
}
