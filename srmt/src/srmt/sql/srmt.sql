/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : srmt

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2016-09-15 15:30:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `patent_info`
-- ----------------------------
DROP TABLE IF EXISTS `patent_info`;
CREATE TABLE `patent_info` (
  `patent_id` varchar(32) NOT NULL DEFAULT '',
  `patent_type` varchar(30) DEFAULT NULL,
  `patent_date` date DEFAULT NULL,
  `patent_creater` varchar(32) DEFAULT NULL,
  `patent_name` varchar(300) DEFAULT NULL,
  `patent_first` varchar(10) DEFAULT NULL,
  `patent_is_transfer` varchar(10) DEFAULT NULL,
  `patent_people` varchar(30) DEFAULT NULL,
  `patent_content` varchar(500) DEFAULT NULL,
  `patent_file` varchar(500) DEFAULT NULL,
  `patent_pass` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`patent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of patent_info
-- ----------------------------
INSERT INTO `patent_info` VALUES ('402881ea5543382c0155435064230008', 'patent_faming', '2016-06-01', '小华', '实用新型专利公报', '1', '1', 'patent_people5', '调查与企业相关产品或技术相关的专利，特别是竞争对手的专利布局及研发动态。从而作为技术参考，跟踪竞争对手的研发方向。', 'E:\\upload\\2016061214320520121222001-卞亚东-基于SSH2的高校教师科研管理 .doc', '3');
INSERT INTO `patent_info` VALUES ('402881ea5543382c01554373faaa000a', 'patent_faming', '2016-06-28', '李明', '测试知识产权', '1', '1', 'patent_people4', '测试知识产权', 'E:\\upload\\2016061215120120121222001-卞亚东-基于SSH2的高校教师科研管理 .doc', '3');
INSERT INTO `patent_info` VALUES ('402881ea5543382c015543740018000b', 'patent_faming', '2016-06-28', '李明', '测试知识产权', '1', '1', 'patent_people4', '测试知识产权', 'E:\\upload\\2016061215120120121222001-卞亚东-基于SSH2的高校教师科研管理 .doc', '3');
INSERT INTO `patent_info` VALUES ('402881ea5543382c015543740407000c', 'patent_faming', '2016-06-28', '李明', '测试知识产权', '1', '1', 'patent_people4', '测试知识产权', 'E:\\upload\\2016061215120120121222001-卞亚东-基于SSH2的高校教师科研管理 .doc', '3');
INSERT INTO `patent_info` VALUES ('402881ea5543791401554380af8e0000', 'patent_shiyong', '2016-05-31', '华丰', '我的申请专利', '1', '1', 'patent_people5', '0', 'E:\\upload\\20160612152554毕业论文（正式）.doc', '3');
INSERT INTO `patent_info` VALUES ('402881ea5543791401554381749c0002', 'patent_waigauan', '2016-06-01', '小华', '跑车外观专利', '1', '1', 'patent_people4', '专利内容', 'E:\\upload\\20160612152644毕业论文（正式）.doc', '1');
INSERT INTO `patent_info` VALUES ('402882e4544cb60d01544cb972010000', 'patent_shiyong', '2016-04-27', '罗英娜', '民航专用配备多功能降落伞式安全逃生服', '1', '1', 'patent_people3', '为了在民航发生空难时, 能给乘客创造一个逃生的机会和一线生机,  本实用新型提供了一种客机应备的民航专用安全逃生服, 它制作简单，穿戴方便。', '', '1');
INSERT INTO `patent_info` VALUES ('402882e454a8f9310154a8fa9bfb0000', 'patent_faming', '2016-05-25', '戴英豪', '汽车知识', '1', '1', 'patent_people2', '戴英豪', '', '2');
INSERT INTO `patent_info` VALUES ('402882e454add8610154add9e5a10000', 'patent_softwar', '2016-05-26', '545t', '45435t', '1', '0', 'patent_people1', '4543t', '', '1');

-- ----------------------------
-- Table structure for `patent_score`
-- ----------------------------
DROP TABLE IF EXISTS `patent_score`;
CREATE TABLE `patent_score` (
  `id` varchar(32) NOT NULL,
  `invent_fist` double DEFAULT '0',
  `invent_is_move` double DEFAULT '0',
  `practical_first` double DEFAULT '0',
  `practical_is_move` double DEFAULT '0',
  `view_first` double DEFAULT '0',
  `view_is_move` double DEFAULT '0',
  `soft_first` double DEFAULT '0',
  `soft_is_move` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of patent_score
-- ----------------------------
INSERT INTO `patent_score` VALUES ('402882e454a475ab0154a476e3370001', '20', '2', '4', '2', '3', '2', '4', '2');

-- ----------------------------
-- Table structure for `project_info`
-- ----------------------------
DROP TABLE IF EXISTS `project_info`;
CREATE TABLE `project_info` (
  `project_id` varchar(32) NOT NULL,
  `project_name` varchar(200) DEFAULT NULL,
  `project_source` varchar(300) DEFAULT NULL,
  `project_fund` double(20,2) DEFAULT NULL,
  `project_first` varchar(100) DEFAULT NULL,
  `project_second` varchar(100) DEFAULT NULL,
  `project_third` varchar(100) DEFAULT NULL,
  `project_type` varchar(20) DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `project_content` varchar(600) DEFAULT NULL,
  `proect_file` varchar(500) DEFAULT NULL,
  `project_pass` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_info
-- ----------------------------
INSERT INTO `project_info` VALUES ('402881ea55431a780155432eed75000a', '项目部管理工程', '济南大学信息学院', '2332444420.00', '李华', '李明', '话', 'project_guo', '2016-06-01', '2016-06-13', '一项教育发展,总占地面积70000平方米,包括:*数幢数层高的小学教学楼*数幢数层高的幼儿园 截止到2016年4月下旬,该项目处于施工图设计单位招标阶段,预计2016年5月确定施工图设计单位.  ', '', '1');
INSERT INTO `project_info` VALUES ('402881ea55431a7801554330a2e6000c', '日加气量27800立方米LNG汽车加气站工程', '济南大学加油站', '3543534.50', '华为', '窦宇', '小明', 'project_shen', '2016-06-01', '2016-06-08', '兴建一座加气站,总建筑面积400平方米,拟建设日加气量27800立方米LNG汽车加气站工程. 截止到2016年5月中旬该项目在立项阶段,预计2016年6月启动设计单位招标.  ', '', '3');
INSERT INTO `project_info` VALUES ('402881ea55437914015543bd996d001a', '省级科研项目', '济南大学', '3454.00', '刘', '', '', 'project_shen', '2016-06-01', '2016-06-08', '开发科研信息', 'E:\\upload\\20160612163217毕业设计正文.doc', '3');
INSERT INTO `project_info` VALUES ('402881ea55437914015543c09fb2001c', '济南大学教师研究项目', '济南大学信息学院', '23423.00', '华丰', '花花', '李明', 'project_guo', '2016-06-01', '2016-06-08', '济南大学教师研究项目', 'E:\\upload\\20160612163542毕业设计正文.doc', '3');
INSERT INTO `project_info` VALUES ('402881ea55437914015543c2c995001e', '计算机研究项目', '信息科学与工程学院', '3543.00', '邓建民', '卞亚东', '管红光', 'proect_other', '2016-06-01', '2016-06-08', '计算机研究项目', 'E:\\upload\\20160612163805毕业设计正文.docx', '3');
INSERT INTO `project_info` VALUES ('402881ea55437914015543c4d65b0020', '信息科研研究型', '济南大学信息学院', '435.00', '丽台', '李涛', '李明', 'proect_other', '2016-06-03', '2016-06-09', '华为', 'E:\\upload\\20160612164020毕业设计正文.doc', '1');
INSERT INTO `project_info` VALUES ('402881ea55437914015543c611b60022', '建设信息化系统', '济南大学', '3543.00', '丽台', '李涛', '华明', 'proect_other', '2016-06-02', '2016-06-12', '建设信息化系统', 'E:\\upload\\20160612164140毕业设计正文.doc', '3');
INSERT INTO `project_info` VALUES ('402882e4544cb60d01544cbb38e40002', '4R434R', '34T34', '399999008.00', '34', '34', 'project_shen', 'project_shen', '2016-04-14', '2016-04-05', '34R5', '', '1');
INSERT INTO `project_info` VALUES ('402882e454add17e0154add1e2fc0000', '1234w', '34543w', '345.00', '435w', '4354w', 'proect_other', 'proect_other', '2016-04-30', '2016-05-16', '543w', 'E:\\upload\\20160525153713科研工作量计算办法.docx', '1');
INSERT INTO `project_info` VALUES ('402882e654e6c5a60154e6f0b1a40000', '年产20吨医药中间体工程', '济南大学', '488835.06', '小明', '刘峰', '李明', 'proect_other', '2016-05-13', '2016-05-05', '一项工业发展,拟年产20吨医药中间体,包括：*车间*原料库*综合办公楼*门卫及其他配套建筑 截止到2016年5月中旬该项目在立项阶段,预计2016年6月启动设计单位招标.  ', 'E:\\upload\\20160612153253毕业论文_卞亚东201606031054.doc', '1');
INSERT INTO `project_info` VALUES ('402882e654e6c5a60154e6f1a74a0002', '济南大学开发项目', '省教务厅', '4345452.50', '刘明', '小明', '华为', 'project_shen', '2016-05-20', '2016-05-17', '45', 'E:\\upload\\20160612153146毕业论文_卞亚东201606031434.doc', '1');

-- ----------------------------
-- Table structure for `project_score`
-- ----------------------------
DROP TABLE IF EXISTS `project_score`;
CREATE TABLE `project_score` (
  `guoK` double DEFAULT '0',
  `guoFund` double DEFAULT '0',
  `guoFundLast` double DEFAULT '1',
  `shenK` double DEFAULT '0',
  `shenFund` double DEFAULT '0',
  `shenFundLast` double DEFAULT '1',
  `otherK` double DEFAULT '0',
  `otherFund` double DEFAULT '0',
  `otherFundLast` double DEFAULT '1',
  `id` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_score
-- ----------------------------
INSERT INTO `project_score` VALUES ('1', '23', '3454356565', '1', '34', '345654546', '1', '34', '345645654', '21218cca77804d2ba1922c33e0151105');

-- ----------------------------
-- Table structure for `reward_info`
-- ----------------------------
DROP TABLE IF EXISTS `reward_info`;
CREATE TABLE `reward_info` (
  `reward_id` varchar(32) NOT NULL,
  `reward_name` varchar(100) DEFAULT NULL,
  `reward_type` varchar(32) DEFAULT NULL,
  `reward_time` date DEFAULT NULL,
  `reward_place` varchar(32) DEFAULT NULL,
  `reward_organ` varchar(32) DEFAULT NULL,
  `reward_level` varchar(10) DEFAULT NULL,
  `reward_user` varchar(300) DEFAULT NULL,
  `reward_file` varchar(300) DEFAULT NULL,
  `reward_content` varchar(300) DEFAULT NULL,
  `reward_pass` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`reward_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reward_info
-- ----------------------------
INSERT INTO `reward_info` VALUES ('402881ea5543382c01554344da900002', '济南大学篮球比赛', 'reward_dishi3', '2016-06-02', 'place_third', '信息科学与工程学院', null, '戴英豪，小明', 'E:\\upload\\20160612142049毕业论文_马超.doc', '此外，学校每年将事业收入部分用于资助在校家庭经济困难学生。学校设立奖学金、宋健奖学金等多种奖励，其中奖学金获奖比例占在校生的23％。', '3');
INSERT INTO `reward_info` VALUES ('402881ea5543382c015543466fb20004', '信息学院征文大赛', 'reward_xiao3', '2016-06-02', 'place_third', '信息学院', null, '管洪钢', 'E:\\upload\\20160612142216毕业设计正文.docx', '文章获取奖励', '1');
INSERT INTO `reward_info` VALUES ('402881ea5543382c015543479e1b0006', '济南大学设计大赛比赛', 'reward_shen1', '2016-06-08', 'place_second', '济南大学', null, '李明', 'E:\\upload\\2016061214233420121222001-卞亚东-基于SSH2的高校教师科研管理 .doc', '济南大学设计大赛比赛奖励，以资鼓励', '1');
INSERT INTO `reward_info` VALUES ('402882e454a8cbc10154a8cd6cfa0000', '济南大学歌唱比赛', 'reward_dishi3', '2016-05-31', 'place_first', '济南大学', null, '小明，留名', 'E:\\upload\\20160612142059毕业设计正文.docx', '345', '2');
INSERT INTO `reward_info` VALUES ('402882e454add5900154add5dd510000', '23r', 'reward_shen1', '2016-05-20', 'place_first', '34543r', null, '543r', 'E:\\upload\\20160525155131科研工作量计算办法.docx', '5435rr', '2');
INSERT INTO `reward_info` VALUES ('ff80808155383a170155383b40450000', '435345热惹他', 'reward_jiao', '2016-06-22', 'place_fourth', '435', null, '435', '', '435', '3');

-- ----------------------------
-- Table structure for `reward_score`
-- ----------------------------
DROP TABLE IF EXISTS `reward_score`;
CREATE TABLE `reward_score` (
  `id` varchar(32) NOT NULL,
  `shen_first` double DEFAULT '0',
  `shen_second` double DEFAULT '0',
  `shen_third` double DEFAULT '0',
  `shen_fourth` double DEFAULT '0',
  `shen_fifth` double DEFAULT '0',
  `shen2_first` double DEFAULT '0',
  `shen2_second` double DEFAULT '0',
  `shen2_third` double DEFAULT '0',
  `shen2_fourth` double DEFAULT '0',
  `shen2_fifth` double DEFAULT '0',
  `shen3_first` double DEFAULT '0',
  `shen3_second` double DEFAULT '0',
  `shen3_third` double DEFAULT '0',
  `shen3_fifth` double DEFAULT '0',
  `shen3_fourth` double DEFAULT '0',
  `dishi_first` double DEFAULT '0',
  `dishi_second` double DEFAULT '0',
  `dishi_third` double DEFAULT '0',
  `dishi2_first` double DEFAULT '0',
  `dishi2_second` double DEFAULT '0',
  `dishi2_third` double DEFAULT '0',
  `dishi3_first` double DEFAULT '0',
  `dishi3_second` double DEFAULT '0',
  `dishi3_third` double DEFAULT '0',
  `school_first` double DEFAULT '0',
  `school_second` double DEFAULT '0',
  `school_third` double DEFAULT '0',
  `youxiu_jiaoxue` double DEFAULT '0',
  `yong_teach` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reward_score
-- ----------------------------
INSERT INTO `reward_score` VALUES ('402882e454a8f9310154a8fa9c1b0001', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '15', '14', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '5.3', '28', '29');

-- ----------------------------
-- Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `dict_id` varchar(32) NOT NULL,
  `dict_name` varchar(100) DEFAULT NULL,
  `dict_value` varchar(100) DEFAULT NULL,
  `is_valid` varchar(10) DEFAULT NULL,
  `dict_type` varchar(50) DEFAULT NULL,
  `seq` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('23423432wer23rwer23r', '地市级二等奖', 'reward_dishi2', '1', 'reward_type', '5');
INSERT INTO `sys_dict` VALUES ('234234dr2342r234', '省部级二等奖', 'reward_shen2', '1', 'reward_type', '2');
INSERT INTO `sys_dict` VALUES ('23687466237yr7823r2', '其它纵向项目和横向项目', 'proect_other', '1', 'project_type', '3');
INSERT INTO `sys_dict` VALUES ('2374723uidyffwewerwe', '论文', 'research_thesis', '1', 'research', null);
INSERT INTO `sys_dict` VALUES ('238yr983rywe78r23r', '省部级三等奖', 'reward_shen3', '1', 'reward_type', '3');
INSERT INTO `sys_dict` VALUES ('2398rtg9sfh87wetfgrtgrt5tyg56', '科研机构', 'patent_people3', '1', 'patent_people', '3');
INSERT INTO `sys_dict` VALUES ('24629ewur298rwerere', '自然科学', 'thesis_type3', '1', 'thesis_type', '3');
INSERT INTO `sys_dict` VALUES ('248932idif234r', '第三位', 'place_third', '1', 'place_type', '3');
INSERT INTO `sys_dict` VALUES ('29yrwyrwe78yywr78wer', '其他教学研究论文', 'thesis_other', '1', 'thesis_Included', '9');
INSERT INTO `sys_dict` VALUES ('2dh872yr8rwerhuZ', '校级一等奖', 'reward_xiao1', '1', 'reward_type', '7');
INSERT INTO `sys_dict` VALUES ('324232423', '软件著作权', 'patent_softwar', '1', 'patent_type', null);
INSERT INTO `sys_dict` VALUES ('3242342342342', '项目', 'research_project', '1', 'research', null);
INSERT INTO `sys_dict` VALUES ('32423423werwer324rwfhyt', '社会', 'thesis_type4', '1', 'thesis_type', '4');
INSERT INTO `sys_dict` VALUES ('32r2836r329r34t435', '个人', 'patent_people5', '1', 'patent_people', '5');
INSERT INTO `sys_dict` VALUES ('32wuer8723r32rwer', '优秀教学奖', 'reward_jiao', '1', 'reward_type', '10');
INSERT INTO `sys_dict` VALUES ('368r328yrhwe8tferefwd', '地市级一等奖', 'reward_dishi1', '1', 'reward_type', '4');
INSERT INTO `sys_dict` VALUES ('3r23r23r23r', '校级二等奖', 'reward_xiao2', '1', 'reward_type', '8');
INSERT INTO `sys_dict` VALUES ('402881ea55446cd70155446fb7c00000', '论文科学', '6f36b9b3-80dd-4949-ba5d-fc9da9448750', '1', 'thesis_type', null);
INSERT INTO `sys_dict` VALUES ('476378yehrre87t8er', '教务处指定的十四种教学期刊', 'thesis_jiaowu', '1', 'thesis_Included', '2');
INSERT INTO `sys_dict` VALUES ('51236277eefwer82334u8r', '外观设计', 'patent_waigauan', '1', 'patent_type', null);
INSERT INTO `sys_dict` VALUES ('62rhy23rh23bwerewky23', '第五位', 'place_fiftth', '1', 'place_type', '5');
INSERT INTO `sys_dict` VALUES ('67237827932jkuyi7rwefwew', '部省级项目', 'project_shen', '1', 'project_type', '2');
INSERT INTO `sys_dict` VALUES ('687ytr7we5rw6t6w7e', 'I收录国际期刊论文', 'thesis_eishou', '1', 'thesis_Included', '4');
INSERT INTO `sys_dict` VALUES ('78237293r23r23', '奖励', 'research_reward', '1', 'research', null);
INSERT INTO `sys_dict` VALUES ('7823rweyr2hys87e6r23r', 'ISTP收录论文', 'thesis_shoulu', '1', 'thesis_Included', '6');
INSERT INTO `sys_dict` VALUES ('78783787834kehurewyr23', 'EI收录论文', 'thesis_eishoulu', '1', 'thesis_Included', '5');
INSERT INTO `sys_dict` VALUES ('79hf93rhy0w7er82o3', '第二位', 'place_second', '1', 'place_type', '2');
INSERT INTO `sys_dict` VALUES ('86hyfsuy7823ry3r32r', '地市级三等奖', 'reward_dishi3', '1', 'reward_type', '6');
INSERT INTO `sys_dict` VALUES ('8723t237ryweuirwr', '实用新型', 'patent_shiyong', '1', 'patent_type', null);
INSERT INTO `sys_dict` VALUES ('87963yrye78ry87yteter', 'SCI收录论文', 'thesis_shousci', '1', 'thesis_Included', '1');
INSERT INTO `sys_dict` VALUES ('8WE7YW8ERE', '省部级一等奖', 'reward_shen1', '1', 'reward_type', '1');
INSERT INTO `sys_dict` VALUES ('ds23ruw9e07r23yhr7w8er23', '高等院校', 'patent_people2', '1', 'patent_people', '2');
INSERT INTO `sys_dict` VALUES ('huihfrewr23ry23uiwer23', 'EI源刊', 'thesis_eiyuan', '1', 'thesis_Included', '3');
INSERT INTO `sys_dict` VALUES ('hweu236432423984723', '第四位', 'place_fourth', '1', 'place_type', '4');
INSERT INTO `sys_dict` VALUES ('i98r2r8934rjoweir23923', '第一位', 'place_first', '1', 'place_type', '1');
INSERT INTO `sys_dict` VALUES ('sdfusfdkuf92398ywerw', '国际会议论文', 'thesis_guoji', '1', 'thesis_Included', '8');
INSERT INTO `sys_dict` VALUES ('sdkfhusdfuweuifwe', '国家级项目', 'project_guo', '1', 'project_type', '1');
INSERT INTO `sys_dict` VALUES ('sfcnwekuhf9238yr34t45', '社会团体', 'patent_people4', '1', 'patent_people', '4');
INSERT INTO `sys_dict` VALUES ('uheiuhu78237327', '专利', 'research_patent', '1', 'research', null);
INSERT INTO `sys_dict` VALUES ('uiehruwerhwieirh23434', '发明专利', 'patent_faming', '1', 'patent_type', null);
INSERT INTO `sys_dict` VALUES ('uiyfsduisiufwer78ttwedf32', '企业', 'patent_people1', '1', 'patent_people', '1');
INSERT INTO `sys_dict` VALUES ('us837uew98ds8fwe832', '中文核心期刊论文', 'thesis_chinese', '1', 'thesis_Included', '7');
INSERT INTO `sys_dict` VALUES ('wsdfwe64y65y55', '论辩型论文', 'thesis_type2', '1', 'thesis_type', '2');
INSERT INTO `sys_dict` VALUES ('wweey54rthy7o89j5j6', '校级三等奖', 'reward_xiao3', '1', 'reward_type', '9');
INSERT INTO `sys_dict` VALUES ('y83463rereiuyferf34nnkv', '青年教学能手', 'reward_qing', '1', 'reward_type', '11');

-- ----------------------------
-- Table structure for `sys_organ`
-- ----------------------------
DROP TABLE IF EXISTS `sys_organ`;
CREATE TABLE `sys_organ` (
  `organ_id` varchar(32) NOT NULL,
  `organ_name` varchar(200) DEFAULT NULL,
  `organ_code` varchar(32) DEFAULT NULL,
  `organ_type` varchar(10) DEFAULT NULL,
  `organ_address` varchar(300) DEFAULT NULL,
  `parent` varchar(32) DEFAULT NULL,
  `is_valid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`organ_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_organ
-- ----------------------------
INSERT INTO `sys_organ` VALUES ('402881ea5543791401554391fe6a0005', '英语专业', '098340', '2', null, '402882e4549e6d1501549e7017600001', '1');
INSERT INTO `sys_organ` VALUES ('402881ea5543791401554392971b0006', '韩语专业', '09836436', '2', null, '402882e4549e6d1501549e7017600001', '1');
INSERT INTO `sys_organ` VALUES ('402881ea55437914015543931b1a0007', '日本语专业', '094395', '2', null, '402882e4549e6d1501549e7017600001', '1');
INSERT INTO `sys_organ` VALUES ('402881ea5543791401554393a6070008', '流行音乐', '0934535', '2', null, '402882e4549e6d1501549e7057820002', '1');
INSERT INTO `sys_organ` VALUES ('402881ea554379140155439495a30009', '越南语专业', '2343567', '2', null, '402882e4549e6d1501549e7017600001', '1');
INSERT INTO `sys_organ` VALUES ('402881ea5543791401554394d41a000a', '软件外包', '34435465', '2', null, '402882e4544c9ca501544ca1a6e00000', '1');
INSERT INTO `sys_organ` VALUES ('402881ea554379140155439632e5000b', '演唱专业', '34354654', '2', null, '402882e4549e6d1501549e7057820002', '1');
INSERT INTO `sys_organ` VALUES ('402882e4544c9ca501544ca1a6e00000', '信息科学与工程学院', '100010', '1', '济南大学', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e4544c9ca501544ca1fd790001', '计算机科学与技术', '100230', '2', '济南大学', '402882e4544c9ca501544ca1a6e00000', '1');
INSERT INTO `sys_organ` VALUES ('402882e4549e6d1501549e7017600001', '外国语学院', '100011', '1', '济南大学济微路外国语学院', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e4549e6d1501549e7057820002', '音乐学院', '100012', '1', '济南大学济微路', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e4549e6d1501549e70a2b60003', '机械学院', '100013', '1', '济南大学济微路机械学院', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e4549e6d1501549e70e8d10004', '物理学院', '100018', '1', '济南大学物理学院88号', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e4549e6d1501549e7438710006', '古典音乐', '34543543', '2', null, '402882e4549e6d1501549e7057820002', '1');
INSERT INTO `sys_organ` VALUES ('402882e454dd4c8a0154dd78558a0004', '数学学院', '100234', '1', '济南大学数学学院关系', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e454dd4c8a0154dd7991e40005', '材料科学与工程学院', '100023', '1', '济南大学材料科学与工程学院', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e454dd4c8a0154dd7a66df0006', '国际教育交流学院', '100028', '1', '济南大学国际教育交流学院留学生管理办公室', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e454dd4c8a0154dd7adaac0007', '历史与文化产业学院', '120934', '1', '历史与文化产业学院', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e454dd4c8a0154dd7b2bee0008', '医学与生命科学学院', '34534534', '1', '医学与生命科学学院', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e454dd4c8a0154dd7be17a000a', '物理科学与技术学院', '235345', '1', '物理科学与技术学院', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e454dd7da30154dd7dbeb30002', '马克思主义学院', '345345', '1', '马克思主义学院', null, '1');
INSERT INTO `sys_organ` VALUES ('402882e454dd7da30154dd83941c0003', '外语专业', '234354654', '2', null, '402882e454dd4c8a0154dd7a66df0006', '1');
INSERT INTO `sys_organ` VALUES ('ff808081552e917c01552ea155fc0000', '网路工程', '354656765', '2', null, '402882e4544c9ca501544ca1a6e00000', '1');
INSERT INTO `sys_organ` VALUES ('ff80808155404afd0155404e5fce0000', '软件开发', '4354654', '2', null, '402882e4544c9ca501544ca1a6e00000', '1');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `login_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) NOT NULL,
  `mobile` varchar(32) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `user_type` varchar(32) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `organ_id` varchar(32) DEFAULT NULL,
  `adress` varchar(246) DEFAULT NULL,
  `id_card_type` varchar(10) DEFAULT NULL,
  `id_card` varchar(64) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `is_valid` varchar(10) DEFAULT NULL,
  `is_admin` varchar(10) DEFAULT NULL,
  `user_num` bigint(12) DEFAULT NULL,
  `dept` varchar(32) DEFAULT NULL,
  `pic_path` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('邓建民', '21218cca77804d2ba1922c33e0151105', null, '402881ea554379140155439116af0004', '18366103818', '970256346@qq.com', '2', '2016-06-08', '402882e4549e6d1501549e7017600001', '山东省济南', null, null, '1', '1', null, '201610000008', '402881ea554379140155439495a30009', null);
INSERT INTO `sys_user` VALUES ('管洪钢', '21218cca77804d2ba1922c33e0151105', null, '402881ea55437914015543976585000c', '18366101346', '87563489549@qq.com', '1', '2016-06-08', '402882e4549e6d1501549e7017600001', '济南大学学23 408', null, null, '1', '1', null, '201610000009', '402881ea5543791401554392971b0006', null);
INSERT INTO `sys_user` VALUES ('房敬超', '21218cca77804d2ba1922c33e0151105', null, '402881ea55437914015543985cbe000d', '18366102979', '2347325643@qq.com', '2', '2016-03-17', '402882e4549e6d1501549e7057820002', '济南大学学23 408', null, null, '1', '1', null, '201610000010', '402882e4549e6d1501549e7438710006', null);
INSERT INTO `sys_user` VALUES ('邓小名', '21218cca77804d2ba1922c33e0151105', null, '402881ea55437914015543997404000e', '18366109384', '238462376@qq.com', '2', '2016-06-16', '402882e4549e6d1501549e7057820002', '济南大学学23 408', null, null, '0', '1', null, '201610000011', '402881ea554379140155439632e5000b', null);
INSERT INTO `sys_user` VALUES ('小红', '21218cca77804d2ba1922c33e0151105', null, '402881ea554379140155439ba2da000f', '18372663423', '345635@qq.com', '1', '2016-06-23', '402882e4549e6d1501549e7057820002', '济南大学济微路外国语学院', null, null, '0', '1', null, '201610000012', '402882e4549e6d1501549e7438710006', null);
INSERT INTO `sys_user` VALUES ('李宁', '21218cca77804d2ba1922c33e0151105', null, '402882e4544c73c801544c73db9f0000', '13534534543', 'wetsgd@qq.com', '1', '2016-04-18', '402882e4549e6d1501549e7017600001', 'werwer', null, null, '1', '1', '1', '201610000001', '402881ea5543791401554392971b0006', null);
INSERT INTO `sys_user` VALUES ('刘明', '21218cca77804d2ba1922c33e0151105', null, '402882e454b9fb300154ba0b69c90000', '18376276350', 'insanrtret@qq.com', '2', '2016-05-12', '402882e4544c9ca501544ca1a6e00000', '', null, null, '1', '0', '1', '201610000004', '402882e4544c9ca501544ca1fd790001', null);
INSERT INTO `sys_user` VALUES ('刘芳', '21218cca77804d2ba1922c33e0151105', null, '402882e454dd4c8a0154dd5ae2490000', '18366109343', '35324354@qq.com', '2', '2016-05-24', '402882e4549e6d1501549e7057820002', '济南大学', null, null, '0', '1', '0', '201610000005', '402881ea5543791401554393a6070008', null);
INSERT INTO `sys_user` VALUES ('马明名', '21218cca77804d2ba1922c33e0151105', null, '402882e454dd4c8a0154dd72f9840003', '18366109876', '34534hsuyf@qq.com', '2', '2016-05-31', '402882e4544c9ca501544ca1a6e00000', '济南大学信息学院', null, null, '0', '1', '0', '201610000006', '402882e4544c9ca501544ca1fd790001', null);
INSERT INTO `sys_user` VALUES ('戴英豪', '63a9f0ea7bb98050796b649e85481845', 'dyh', 'eri72y38723rgy378wry8w87er', '18366107703', '864422716@qq.com', '2', '2016-04-20', '402882e4549e6d1501549e7057820002', '济南大学学23 408', null, null, '1', '1', '0', '201610000003', '402882e4549e6d1501549e7438710006', 'C:\\Users\\Instant\\Pictures\\Camera Roll\\1.jpg');
INSERT INTO `sys_user` VALUES ('新增', '21218cca77804d2ba1922c33e0151105', null, 'ff8080815500d51c015500e5f3970000', '18376780987', '8hwey67rewhy7@qq.com', '1', '2016-05-05', '402882e4544c9ca501544ca1a6e00000', '', null, null, '0', '1', null, '201610000007', 'ff808081552e917c01552ea155fc0000', null);
INSERT INTO `sys_user` VALUES ('卞亚东', '63a9f0ea7bb98050796b649e85481845', 'root', 'root', '18366101891', 'bydpro@gmail.com', '1', '2016-04-27', '402882e4544c9ca501544ca1a6e00000', '济南大学学23 408', null, null, '1', '1', '1', '201610000002', '402882e4544c9ca501544ca1fd790001', null);

-- ----------------------------
-- Table structure for `sys_user_research`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_research`;
CREATE TABLE `sys_user_research` (
  `rid` varchar(32) NOT NULL,
  `research_user_id` varchar(32) DEFAULT NULL,
  `research_id` varchar(32) DEFAULT NULL,
  `research_type` varchar(32) DEFAULT NULL,
  `creater` varchar(32) DEFAULT NULL,
  `creater_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_research
-- ----------------------------
INSERT INTO `sys_user_research` VALUES ('402881ea55431a78015543228a4e0001', '201610000003', '402881ea55431a78015543228a420000', 'research_thesis', 'eri72y38723rgy378wry8w87er', '2016-06-12 13:43:05');
INSERT INTO `sys_user_research` VALUES ('402881ea55431a78015543248b0f0003', '201610000003', '402881ea55431a78015543248b0f0002', 'research_thesis', 'eri72y38723rgy378wry8w87er', '2016-06-12 13:45:16');
INSERT INTO `sys_user_research` VALUES ('402881ea55431a780155432650870005', '201610000003', '402881ea55431a780155432650870004', 'research_thesis', 'eri72y38723rgy378wry8w87er', '2016-06-12 13:47:12');
INSERT INTO `sys_user_research` VALUES ('402881ea55431a78015543279ac60007', '201610000003', '402881ea55431a78015543279ac50006', 'research_thesis', 'eri72y38723rgy378wry8w87er', '2016-06-12 13:48:36');
INSERT INTO `sys_user_research` VALUES ('402881ea55431a780155432eed75000b', '201610000003', '402881ea55431a780155432eed75000a', 'research_project', 'eri72y38723rgy378wry8w87er', '2016-06-12 13:56:36');
INSERT INTO `sys_user_research` VALUES ('402881ea55431a7801554330a2e6000d', '201610000003', '402881ea55431a7801554330a2e6000c', 'research_project', 'eri72y38723rgy378wry8w87er', '2016-06-12 13:58:28');
INSERT INTO `sys_user_research` VALUES ('402881ea5543382c01554344da900003', '201610000003', '402881ea5543382c01554344da900002', 'research_reward', 'eri72y38723rgy378wry8w87er', '2016-06-12 14:20:33');
INSERT INTO `sys_user_research` VALUES ('402881ea5543382c015543466fb20005', '201610000003', '402881ea5543382c015543466fb20004', 'research_reward', 'eri72y38723rgy378wry8w87er', '2016-06-12 14:22:17');
INSERT INTO `sys_user_research` VALUES ('402881ea5543382c015543479e1b0007', '201610000003', '402881ea5543382c015543479e1b0006', 'research_reward', 'eri72y38723rgy378wry8w87er', '2016-06-12 14:23:34');
INSERT INTO `sys_user_research` VALUES ('402881ea5543382c0155435064230009', '201610000003', '402881ea5543382c0155435064230008', 'research_patent', 'eri72y38723rgy378wry8w87er', '2016-06-12 14:33:09');
INSERT INTO `sys_user_research` VALUES ('402881ea5543791401554380af9c0001', '201610000003', '402881ea5543791401554380af8e0000', 'research_patent', 'eri72y38723rgy378wry8w87er', '2016-06-12 15:25:54');
INSERT INTO `sys_user_research` VALUES ('402881ea5543791401554381749c0003', '201610000003', '402881ea5543791401554381749c0002', 'research_patent', 'eri72y38723rgy378wry8w87er', '2016-06-12 15:26:45');
INSERT INTO `sys_user_research` VALUES ('402881ea55437914015543abe51d0011', '201610000001', '402881ea55437914015543abe51d0010', 'research_thesis', 'root', '2016-06-12 16:13:06');
INSERT INTO `sys_user_research` VALUES ('402881ea55437914015543b3eacb0013', '201610000002', '402881ea55437914015543b3eacb0012', 'research_thesis', 'root', '2016-06-12 16:21:52');
INSERT INTO `sys_user_research` VALUES ('402881ea55437914015543b9acdf0015', '201610000004', '402881ea55437914015543b9acdf0014', 'research_thesis', 'root', '2016-06-12 16:28:09');
INSERT INTO `sys_user_research` VALUES ('402881ea55437914015543bad22e0017', '201610000005', '402881ea55437914015543bad22e0016', 'research_thesis', 'root', '2016-06-12 16:29:24');
INSERT INTO `sys_user_research` VALUES ('402881ea55437914015543bbdcf10019', '201610000008', '402881ea55437914015543bbdcf10018', 'research_thesis', 'root', '2016-06-12 16:30:33');
INSERT INTO `sys_user_research` VALUES ('402881ea55437914015543bd996d001b', '201610000004', '402881ea55437914015543bd996d001a', 'research_project', 'root', '2016-06-12 16:32:26');
INSERT INTO `sys_user_research` VALUES ('402881ea55437914015543c09fb3001d', '201610000006', '402881ea55437914015543c09fb2001c', 'research_project', 'root', '2016-06-12 16:35:45');
INSERT INTO `sys_user_research` VALUES ('402881ea55437914015543c2c995001f', '201610000007', '402881ea55437914015543c2c995001e', 'research_project', 'root', '2016-06-12 16:38:06');
INSERT INTO `sys_user_research` VALUES ('402881ea55437914015543c4d65b0021', '201610000009', '402881ea55437914015543c4d65b0020', 'research_project', 'root', '2016-06-12 16:40:21');
INSERT INTO `sys_user_research` VALUES ('402881ea55437914015543c611b60023', '201610000009', '402881ea55437914015543c611b60022', 'research_project', 'root', '2016-06-12 16:41:42');
INSERT INTO `sys_user_research` VALUES ('402882e4544cb60d01544cb9721d0001', '201610000001', '402882e4544cb60d01544cb972010000', 'research_patent', 'eri72y38723rgy378wry8w87er', '2016-04-25 17:21:42');
INSERT INTO `sys_user_research` VALUES ('402882e4544cb60d01544cbb39280003', '201610000002', '402882e4544cb60d01544cbb38e40002', 'research_project', 'eri72y38723rgy378wry8w87er', '2016-04-25 17:23:38');
INSERT INTO `sys_user_research` VALUES ('402882e454a475ab0154a476e3370001', '201610000003', '402882e454a475ab0154a476e3220000', 'research_thesis', 'eri72y38723rgy378wry8w87er', '2016-05-12 18:15:35');
INSERT INTO `sys_user_research` VALUES ('402882e454a8cbc10154a8cd6d070001', '201610000003', '402882e454a8cbc10154a8cd6cfa0000', 'research_reward', 'eri72y38723rgy378wry8w87er', '2016-05-13 14:28:35');
INSERT INTO `sys_user_research` VALUES ('402882e454a8f9310154a8fa9c1b0001', '201610000003', '402882e454a8f9310154a8fa9bfb0000', 'research_patent', 'eri72y38723rgy378wry8w87er', '2016-05-13 15:17:56');
INSERT INTO `sys_user_research` VALUES ('402882e454add17e0154add1e3770001', '201610000002', '402882e454add17e0154add1e2fc0000', 'research_project', 'root', '2016-05-14 13:51:34');
INSERT INTO `sys_user_research` VALUES ('402882e454add5900154add5dd710001', '201610000002', '402882e454add5900154add5dd510000', 'research_reward', 'root', '2016-05-14 13:55:54');
INSERT INTO `sys_user_research` VALUES ('402882e454add8610154add9e61e0001', '201610000002', '402882e454add8610154add9e5a10000', 'research_patent', 'root', '2016-05-14 14:00:19');
INSERT INTO `sys_user_research` VALUES ('402882e454dd8c3c0154dd8fe62f0001', '201610000006', '402882e454dd8c3c0154dd8fe6170000', 'research_thesis', 'eri72y38723rgy378wry8w87er', '2016-05-23 20:21:15');
INSERT INTO `sys_user_research` VALUES ('402882e654e6c5a60154e6f0b26d0001', '201610000003', '402882e654e6c5a60154e6f0b1a40000', 'research_project', 'eri72y38723rgy378wry8w87er', '2016-05-25 16:03:34');
INSERT INTO `sys_user_research` VALUES ('402882e654e6c5a60154e6f1a74b0003', '201610000003', '402882e654e6c5a60154e6f1a74a0002', 'research_project', 'eri72y38723rgy378wry8w87er', '2016-05-25 16:04:37');
INSERT INTO `sys_user_research` VALUES ('ff80808155383a170155383b405f0001', '201610000001', 'ff80808155383a170155383b40450000', 'research_reward', 'root', '2016-06-10 10:54:15');

-- ----------------------------
-- Table structure for `thesis_info`
-- ----------------------------
DROP TABLE IF EXISTS `thesis_info`;
CREATE TABLE `thesis_info` (
  `thesis_id` varchar(32) NOT NULL,
  `thesis_name` varchar(100) DEFAULT NULL,
  `thesis_periodical` varchar(300) DEFAULT NULL,
  `thesis_date` date DEFAULT NULL,
  `thesis_author` varchar(300) DEFAULT NULL,
  `thesis_record` varchar(300) DEFAULT NULL,
  `thesis_file` varchar(300) DEFAULT NULL,
  `thesis_type` varchar(40) DEFAULT NULL,
  `thesis_abstract` varchar(500) DEFAULT NULL,
  `thesis_page` varchar(200) DEFAULT NULL,
  `thesis_pass` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`thesis_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of thesis_info
-- ----------------------------
INSERT INTO `thesis_info` VALUES ('402881ea55431a78015543228a420000', '对经济学在艺术院校教学中的不足进行研究', 'thesis_shousci', '2016-06-09', '刘明', '《Science》', 'E:\\upload\\2016061213430320121222001-卞亚东-基于SSH2的高校教师科研管理 .doc', 'thesis_type3', '网红经济的泡泡吹得五彩斑斓，投资人关起门来还是理性地打算盘，精神文明、审美、情怀、消费升级这些被赋予的意义删繁就简后，只剩下一条接地气的线性逻辑，网红的实质是生产流量和吸引流量的单元，而流量最终要能够变现。“自有流量、精准转化”是互联网的真理。网红从电商领域崛起，因为这是离钱最近、流量日益昂贵和稀缺的领域。讨论风口与未来，也得基于流量的故事，谁长期拥有流量，谁就是有价值的，如果新科技、新硬件发展成新的流量入口，那也许就是网红退场、重建格局的时刻。', '313(5789):940-943', '3');
INSERT INTO `thesis_info` VALUES ('402881ea55431a78015543248b0f0002', '一致粘弹性人工边界及粘弹性边界单元', 'thesis_shoulu', '2016-06-01', '刘晶波，谷音 ，杜义欣 ', '岩土工程学报', 'E:\\upload\\20160612134515毕业论文（正式）20160601.doc', 'thesis_type3', '提出了一致粘弹性人工边界及粘弹性人工边界单元的概念,推导了二维一致粘弹性人工边界单元刚度及阻尼矩阵,并在其基础上使用矩阵等效原理实现了采用普通有限单元模拟的二维粘弹性边界单元.均匀半空间算例与成层半空间算例验证了粘弹性边界单元具有与集中粘弹性人工边界相同的精度,并且实施更为简便.', '2006, 28(9)', '1');
INSERT INTO `thesis_info` VALUES ('402881ea55431a780155432650870004', '新型三角形弹簧质点模型及弹簧参数确定', 'thesis_jiaowu', '2016-06-02', '刘凯 ，张纯 ， 夏茵 ', '计算机辅助设计与图形学学报', 'E:\\upload\\20160612134711毕业论文_卞亚东201606031434.doc', 'thesis_type4', '针对任意单元形状和材料参数,通过引入附加节点和附加弹簧建立了一种三角形弹簧质点模型.首先给出弹簧刚度参数及附加节点坐标的解析公式,以实现有限元模型和弹簧质点模型三角形单元刚度矩阵的精确相等;然后考虑弹簧质点模型中可能出现的负刚度弹簧,进一步完善了弹簧质点模型形变计算方法.基于平面柔性体的数值模拟结果表明,文中提出的弹簧质点模型和形变计算方法精度好、效率高,且更具通用性.', '2014, 26(1)', '3');
INSERT INTO `thesis_info` VALUES ('402881ea55431a78015543279ac50006', '三维一致粘弹性人工边界及等效粘弹性边界单元', 'thesis_chinese', '2016-06-16', '谷音，刘晶波，杜义欣', '工程力学', 'E:\\upload\\20160612134942毕业论文（正式）.doc', 'thesis_type4', '摘要：基于粘弹性人工边界推导了三维一致粘弹性人工边界单元的刚度及阻尼矩阵,利用单元矩阵等效原理采用普通有限单元构造了等效粘弹性边界单元来模拟三维粘弹性边界.均匀半空间算例与成层半空间算例证明三维粘弹性边界单元具有与集中粘弹性人工边界相近的精度,并且施加更为简便.', '2007, 24(12)', '3');
INSERT INTO `thesis_info` VALUES ('402881ea55437914015543abe51d0010', '探析我国高等学校多媒体教学的困境与原因', 'thesis_chinese', '2016-06-20', '刘方', '工程力学', 'E:\\upload\\20160612161242毕业论文（正式）.doc', 'thesis_type4', '现代教育技术在高等教育教学中的运用越来越为广泛,尤其是多媒体教学凭借其形象、生动及提高教学效率等优势使得多媒体教学在高校教学中所占比重日趋扩大,当然也为教学带来了一定的影响。本文将从从理论和实践两个层面,阐释我国高等院校在多媒体教学中存在的问题,并比较全面分析了产生问题的原因。', '2007, 24(12)', '3');
INSERT INTO `thesis_info` VALUES ('402881ea55437914015543b3eacb0012', '让孩子成为信息技术课主角的实践探索', 'thesis_guoji', '2016-06-21', '李华', '社会科学期刊', 'E:\\upload\\20160612162151毕业论文（正式）20160601.doc', 'thesis_type4', '让孩子成为信息技术课主角的实践探索', '2007, 24(12)', '3');
INSERT INTO `thesis_info` VALUES ('402881ea55437914015543b9acdf0014', '模因论与社会语用', 'thesis_other', '2016-06-02', '何自然，何雪林', '《现代外语》', 'E:\\upload\\20160612162808毕业论文（正式）.doc', 'thesis_type3', '模因是文化信息单位,像基因那样得到继承,像病毒那样得到传播。它可以在人与人之间传染。由于散播模因的个人继续携带着模因,所以这种传染就理解为复制,即在另一个人的', '2003, 26(2)', '3');
INSERT INTO `thesis_info` VALUES ('402881ea55437914015543bad22e0016', '流动民工的社会网络和社会地位', 'thesis_shousci', '2016-06-08', '李培林', '《社会学研究》', 'E:\\upload\\2016061216285520121222001-卞亚东-基于SSH2的高校教师科研管理 .doc', 'thesis_type3', '流动民工研究已成为近年来学术界研究的热点,社会学的参与推动了流动民工研究的地进一步深化。本文从流动民工的社会网络和交往方式、流动民工的生活状况、流动民工的社.', '1996(4):42-52', '3');
INSERT INTO `thesis_info` VALUES ('402881ea55437914015543bbdcf10018', '社会-经济-自然复合生态系统', 'thesis_eiyuan', '2016-06-03', '马世骏，王如松', '《生态学报》', 'E:\\upload\\20160612163001毕业设计正文.docx', 'thesis_type3', '当代若干重大社会问题,都直接 或间接关系到社会体制、经济发展状况以及人类赖以生存的自然环境。社会、经济和自然是三个不同性质的系统,但其各自的生存和发展都受其它.', ' 1984, 27(1):1-9', '3');
INSERT INTO `thesis_info` VALUES ('402882e454a475ab0154a476e3220000', '我的论文题目', 'thesis_eiyuan', '2016-06-21', '士大夫，卞亚东', '问问', 'E:\\upload\\20160525160100科研工作量计算办法.docx', 'thesis_type4', '士大夫但是方式时发生的故事', '2007, 24(12)', '1');
INSERT INTO `thesis_info` VALUES ('402882e454dd8c3c0154dd8fe6170000', '毕业论文谁', 'thesis_jiaowu', '2016-06-14', '微软，大概豆腐干，', '工程力学', '', 'thesis_type3', '发射点', '2007, 24(12)', '1');

-- ----------------------------
-- Table structure for `thesis_score`
-- ----------------------------
DROP TABLE IF EXISTS `thesis_score`;
CREATE TABLE `thesis_score` (
  `thesis_eiyuan` double DEFAULT '0',
  `thesis_eishoulu` double DEFAULT '0',
  `thesis_shousci` double DEFAULT '0',
  `thesis_shoulu` double DEFAULT '0',
  `thesis_guoji` double DEFAULT '0',
  `thesis_other` double DEFAULT '0',
  `thesis_chinese` double DEFAULT '0',
  `thesis_eishou` double DEFAULT '0',
  `thesis_jiaowu` double DEFAULT '0',
  `id` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of thesis_score
-- ----------------------------
INSERT INTO `thesis_score` VALUES ('4', '1.5', '1.5', '3', '1', '1', '1.5', '1', '2', '402882e4544caa0401544cb505650000');
