package srmt.java.common;

import java.io.File;

public class Constants {

	public static final String VALIDATE_CODE_KEY = "VALIDATE_CODE_KEY";// 验证码key
	public static final String VALIDATE_CODE_TIME = "VALIDATE_CODE_TIME";// 生成验证码时间
	public static final long IS_VALID_TIME = 100;// 验证码有效时间
	public static final String YES = "1";//
	public static final String NO = "0";//
	public static final String DEFULT_PASSWORD = "888888";// 生成验证码时间

	public static final String USER_TYPE_ADMIN = "1";// 用户类型管理员
	public static final String USER_TYPE_TEC = "2";// 用户类型教师

	public static final String DICT_TYPE_RESEARCH = "research";// 用户类型教师

	public static final String RESEARCH_TYPE_THESIS = "research_thesis";// 科研类型论文
	public static final String RESEARCH_TYPE_PROJECT = "research_project";// 科研类型项目
	public static final String RESEARCH_TYPE_REWARD = "research_reward";// 科研类型奖励
	public static final String RESEARCH_TYPE_PATENT = "research_patent";// 科研类型专利

	public static final String DICT_PROJECT_TYPE = "project_type";// 项目类型字典值
	public static final String DICT_PATENT_TYPE = "patent_type";// 专利类型字典值
	public static final String DICT_PLACE_TYPE = "place_type";// 位次
	public static final String DICT_REWARD_TYPE = "reward_type";// 获奖类型
	public static final String DICT_THESIS_INCLUDED = "thesis_Included";// 获奖类型
	public static final String DICT_THESIS_TYPE = "thesis_type";// 获奖类型
	public static final String DICT_PATENT_PEOPLE = "patent_people";// 获奖类型

	public static final String USER_ID = "userId";// 获奖类型

	public static final String FILE_UPLOAD_PATH = "E:" + File.separator + "upload";// 上传文件路径

	public static final String THESIS_INCLUDED_EIYUAN = "thesis_eiyuan";// EI源刊
	public static final String THESIS_INCLUDED_EISHOULU = "thesis_eishoulu";// EI收录论文
	public static final String THESIS_INCLUDED_SHOUSCI = "thesis_shousci";// SCI收录论文
	public static final String THESIS_INCLUDED_SHOULU = "thesis_shoulu";// ISTP收录论文
	public static final String THESIS_INCLUDED_GUOJI = "thesis_guoji";// 国际会议论文
	public static final String THESIS_INCLUDED_OTHER = "thesis_other";// 其他教学研究论文
	public static final String THESIS_INCLUDED_CHINESE = "thesis_chinese";// 中文核心期刊论文
	public static final String THESIS_INCLUDED_EISHOU = "thesis_eishou";// EI收录国际期刊论文
	public static final String THESIS_INCLUDED_JIAOWU = "thesis_jiaowu";// 教务处指定的十四种教学期刊
	
	public static final String PROJECT_TYPE__GUO = "project_guo";// 国家级项目
	public static final String PROJECT_TYPE_OTHER = "proect_other";// 其它纵向项目和横向项目
	public static final String PROJECT_TYPE_SHEN= "project_shen";// 部省级项目
	
	public static final double PROECT_TIAO_K= 1;// 部省级项目
	
	public static final String PLACE_TYPE_FIRST= "place_first";// 第二位
	public static final String PLACE_TYPE_SECOND= "place_second";// 第二位
	public static final String PLACE_TYPE_THIRD= "place_third";// 第三位
	public static final String PLACE_TYPE_FOURTH= "place_fourth";// 第四位
	public static final String PLACE_TYPE_FIFTTH= "place_fiftth";// 第五位
	
	public static final String REWARD_TYPE_QING= "reward_qing";// 青年教学能手
	public static final String REWARD_TYPE_DISHI1= "reward_dishi1";// 地市级一等奖
	public static final String REWARD_TYPE_DISHI2= "reward_dishi2";//地市级二等奖
	public static final String REWARD_TYPE_DISHI3= "reward_dishi3";//地市级三等奖
	public static final String REWARD_TYPE_SHEN3= "reward_shen3";//省部级三等奖
	public static final String REWARD_TYPE_JIAO= "reward_jiao";//优秀教学奖
	public static final String REWARD_TYPE_XIAO1= "reward_xiao1";//校级一等奖
	public static final String REWARD_TYPE_XIAO2= "reward_xiao2";//校级二等奖
	public static final String REWARD_TYPE_XIAO3= "reward_xiao3";//校级三等奖
	public static final String REWARD_TYPE_SHEN2= "reward_shen2";//省部级二等奖
	public static final String REWARD_TYPE_SHEN1= "reward_shen1";//省部级一等奖
	
	public static final String PATENT_TYPE_WAIGAUAN= "patent_waigauan";//外观设计
	public static final String PATENT_TYPE_FAMING= "patent_faming";//发明专利
	public static final String PATENT_TYPE_SHIYONG= "patent_shiyong";//实用新型
	public static final String PATENT_TYPE_SOFTWAR= "patent_softwar";//软件著作权
	
	public static final Integer USER_NUM_MIN = 10000001;//用户号码最小
	public static final Integer USER_NUM_MAX = 99999999;//用户号码最小
}
