package srmt.java.common;

import java.io.File;

public class Constants {

	public static final String VALIDATE_CODE_KEY = "VALIDATE_CODE_KEY";//验证码key
	public static final String VALIDATE_CODE_TIME = "VALIDATE_CODE_TIME";//生成验证码时间
	public static final long  IS_VALID_TIME = 100;//验证码有效时间
	public static final String YES = "1";//生成验证码时间
	public static final String NO = "0";//生成验证码时间
	public static final String DEFULT_PASSWORD = "888888";//生成验证码时间
	
	public static final String USER_TYPE_ADMIN = "1";//用户类型管理员
	public static final String USER_TYPE_TEC = "2";//用户类型教师
	
	public static final String DICT_TYPE_RESEARCH = "research";//用户类型教师
	
	public static final String RESEARCH_TYPE_THESIS = "research_thesis";//科研类型论文
	public static final String RESEARCH_TYPE_PROJECT = "research_project";//科研类型项目
	public static final String RESEARCH_TYPE_REWARD = "research_reward";//科研类型奖励
	public static final String RESEARCH_TYPE_PATENT = "research_patent";//科研类型专利
	
	public static final String DICT_PROJECT_TYPE= "project_type";//项目类型字典值
	public static final String DICT_PATENT_TYPE= "patent_type";//专利类型字典值
	public static final String DICT_PLACE_TYPE= "place_type";//位次
	public static final String DICT_REWARD_TYPE= "reward_type";//获奖类型
	public static final String DICT_THESIS_INCLUDED= "thesis_Included";//获奖类型
	public static final String DICT_THESIS_TYPE= "thesis_type";//获奖类型
	public static final String DICT_PATENT_PEOPLE= "patent_people";//获奖类型
	
	public static final String USER_ID= "userId";//获奖类型
	
	public static final String FILE_UPLOAD_PATH= "E:"+File.separator+"upload";//上传文件路径
}
