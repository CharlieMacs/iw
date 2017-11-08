DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '用户id，发起方的user.id，我的id，发起关注的人',
  `othersid` int(11) DEFAULT NULL COMMENT 'userid这个人关注的其他用户，被关注人',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户关注表';
-----
BEGIN;
INSERT INTO `collect` VALUES ('4', '1', '44', '1507694123');
COMMIT;
-----
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `self` int(10) unsigned NOT NULL COMMENT '自己，例如QQ登录后，这个QQ的账号.此处关联user.id',
  `other` int(10) unsigned NOT NULL COMMENT '自己，例如QQ登录后，这个QQ的好友的账号,此处关联user.id',
  PRIMARY KEY (`id`),
  KEY `self` (`self`),
  KEY `other` (`other`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='加好友，主表';
-----
DROP TABLE IF EXISTS `friend_log`;
CREATE TABLE `friend_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '对应friend.id',
  `self` int(10) unsigned NOT NULL COMMENT '主动添加请求的user.id',
  `other` int(10) unsigned NOT NULL COMMENT '被动接受好友添加的user.id',
  `time` int(10) unsigned NOT NULL COMMENT '执行此操作当前的时间，UNIX时间戳',
  `state` tinyint(3) unsigned NOT NULL COMMENT '1：主动发出添加好友申请;2：被动者接受或者拒绝好友申请;3：删除好友',
  `ip` char(15) COLLATE utf8_unicode_ci NOT NULL COMMENT '当前操作者的ip地址',
  PRIMARY KEY (`id`),
  KEY `self` (`self`,`other`,`time`,`state`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='好友操作日志记录';
-----
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `senderid` int(11) unsigned NOT NULL COMMENT '自己的userid，发信人的userid，若是为0则为系统信息',
  `recipientid` int(11) unsigned NOT NULL COMMENT '给谁发信，这就是谁的userid，目标用户的userid，收信人id',
  `time` int(11) unsigned NOT NULL COMMENT '此信息的发送时间',
  `state` tinyint(3) unsigned NOT NULL COMMENT '0:未读，1:已读 ，2已删除',
  `isdelete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否已经被删除。0正常，1已删除，',
  PRIMARY KEY (`id`),
  KEY `self` (`senderid`,`recipientid`,`time`,`state`,`isdelete`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='发信箱，发信，用户发信表';
-----
BEGIN;
INSERT INTO `message` VALUES ('1', '44', '1', '1448608005', '1', '0'), ('2', '1', '44', '1448608059', '1', '1'), ('45', '1', '44', '1507691700', '0', '0');
COMMIT;
-----
DROP TABLE IF EXISTS `message_data`;
CREATE TABLE `message_data` (
  `id` int(11) unsigned NOT NULL COMMENT '对应message.id',
  `content` varchar(400) COLLATE utf8_unicode_ci NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='对应message，存储内容';

BEGIN;
INSERT INTO `message_data` VALUES ('1', 'wer'), ('2', 'wer啊手机打开'), ('45', '2332');
COMMIT;

DROP TABLE IF EXISTS `pay_log`;
CREATE TABLE `pay_log` (
  `id` int(111) NOT NULL AUTO_INCREMENT,
  `channel` char(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付通道，alipay：支付宝； wx：微信，同ping++的channel，若中间有下划线，去掉下划线。',
  `addtime` int(11) DEFAULT NULL COMMENT '支付时间',
  `money` float(6,2) DEFAULT NULL COMMENT '付款金额，单位：元',
  `orderno` char(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '订单号，2个随机数＋10位linux时间戳',
  `userid` int(11) DEFAULT NULL COMMENT '支付的用户，关联user.id',
  PRIMARY KEY (`id`),
  KEY `orderno` (`orderno`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='支付日志记录';
-----
BEGIN;
INSERT INTO `pay_log` VALUES ('1', 'wx', '1463652551', '0.10', '23434234234', '27'), ('4', 'alipay', '1463983338', '0.40', '141463983296', '40');
COMMIT;
-----
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` char(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述信息，备注，只是给后台设置权限的人看的',
  `url` char(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '资源url',
  `name` char(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名字，菜单的名字，显示给用户的',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级资源的id',
  `percode` char(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`,`name`,`percode`),
  KEY `parent_id` (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;
-----
BEGIN;
INSERT INTO `permission` VALUES ('1', '用户前台用户的个人中心', '/user/info.do', '个人中心', '0', 'user'), ('2', '用户前台用户的个人中心', '/user/info.do', '我的信息', '1', 'userIndex'), ('3', '前端用户在论坛发表帖子', '/bbs/addPost.do', '发帖', '4', 'bbsAddPost'), ('4', '前端，论坛', '/bbs/list.do', '论坛', '0', 'bbs'), ('7', '用户端的我的好友', '/friend/list.do', '我的好友', '0', 'friend'), ('9', '前端的我的好友，添加好友', '/friend/add.do', '添加好友', '7', 'friendAdd'), ('10', '前端，我的好友，好友列表', '/friend/list.do', '好友列表', '7', 'friendList'), ('11', '前端的我的好友，删除好友', '/friend/delete.do', '删除好友', '7', 'friendDelete'), ('12', '后台的用户管理', '/admin/user/list.do', '用户管理', '0', 'adminUser'), ('13', '后台用户管理下的菜单', '/admin/user/list.do', '用户列表', '12', 'adminUserList'), ('14', '后台用户管理下的菜单', '/admin/user/delete.do', '删除用户', '12', 'adminUserDelete'), ('15', '管理后台－系统管理栏目', '/admin/system/index.do', '系统管理', '0', 'adminSystem'), ('16', '管理后台－系统管理－系统参数、系统变量列表', '/admin/system/variableList.do', '全局变量', '15', 'adminSystemVariable'), ('17', '管理后台－系统管理，刷新所有缓存', '/admin/system/generateAllCache.do', '刷新缓存', '15', 'adminSystemGenerateAllCache'), ('18', '前端，用户中心，注销登录', '/user/logout.do', '注销', '1', 'userLogout'), ('19', '前端，用户中心，更改头像', '/user/uploadHead.do', '更改头像', '1', 'userUploadHead'), ('20', '前端，用户中心，更改用户自己的昵称', '/user/updateNickName.do', '更改昵称', '1', 'userUpdateNickName'), ('21', '前端，用户中心，更改密码', '/user/updatePassword.do', '更改密码', '1', 'userUpdatePassword'), ('22', '前端，用户中心，邮件邀请用户注册', '/user/inviteEmail.do', '邮件邀请注册', '1', 'userInviteEmail'), ('23', '前端，好友中心，首页', '/friend/index.do', '首页', '7', 'friendIndex'), ('24', '前端，站内信', '/message/list.do', '信息', '0', 'message'), ('25', '前端，站内信，信息列表', '/message/list.do', '信息列表', '24', 'messageList'), ('26', '前端，站内信，阅读信息', '/message/view.do', '阅读信息', '24', 'messageView'), ('27', '前端，站内信，发送信息', '/message/send.do', '发送信息', '24', 'messageSend'), ('28', '前端，论坛，帖子列表', '/bbs/list.do', '帖子列表', '4', 'bbsList'), ('29', '前端，论坛，帖子详情', '/bbs/view.do', '帖子详情', '4', 'bbsView'), ('30', '前端，论坛，回复帖子', '/bbs/addComment.do', '回复帖子', '4', 'bbsAddComment'), ('44', '后台，权限管理', '/admin/role/roleList.do', '权限管理', '0', 'adminRole'), ('46', '后台，权限管理，新增、编辑角色', '/admin/role/editRole.do', '编辑角色', '44', 'adminRoleRole'), ('48', '后台，权限管理，角色列表', '/admin/role/roleList.do', '角色列表', '44', 'adminRoleRoleList'), ('49', '后台，权限管理，删除角色', '/admin/role/deleteRole.do', '删除角色', '44', 'adminRoleDeleteRole'), ('51', '后台，权限管理，资源Permission的添加、编辑功能', '/admin/role/editPermission.do', '编辑资源', '44', 'adminRolePermission'), ('53', '后台，权限管理，资源Permission列表', '/admin/role/permissionList.do', '资源列表', '44', 'adminRolePermissionList'), ('54', '后台，权限管理，删除资源Permission', '/admin/role/deletePermission.do', '删除资源', '44', 'adminRoleDeletePermission'), ('55', '后台，权限管理，编辑角色下资源', '/admin/role/editRolePermission.do', '编辑角色下资源', '44', 'adminRoleEditRolePermission'), ('56', '后台，权限管理，编辑用户所属角色', '/admin/role/editUserRole.do', '编辑用户所属角色', '44', 'adminRoleEditUserRole'), ('57', '后台，论坛管理', '/admin/bbs/postList.do', '论坛管理', '0', 'adminBbs'), ('58', '后台，论坛管理，帖子列表', '/admin/bbs/postList.do', '帖子列表', '57', 'adminBbsPostList'), ('59', '后台，论坛管理，删除帖子', '/admin/bbs/deletePost.do', '删除帖子', '57', 'adminBbsDeletePost'), ('61', '后台，论坛管理，添加，修改板块', '/admin/bbs/class.do', '修改板块', '57', 'adminBbsClass'), ('117', '后台，日志管理，所有动作的日志图表', '/admin/log/cartogram.do', '统计图表', '71', 'adminLogCartogram'), ('63', '后台，论坛管理，板块列表', '/admin/bbs/classList.do', '板块列表', '57', 'adminBbsClassList'), ('64', '后台，论坛管理，删除板块', '/admin/bbs/deleteClass.do', '删除板块', '57', 'adminBbsDeleteClass'), ('65', '后台，站内信消息管理', '/admin/message/list.do', '消息管理', '0', 'adminMessage'), ('66', '后台，站内信消息管理，消息列表', '/admin/message/list.do', '消息列表', '65', 'adminMessageList'), ('67', '后台，站内信消息管理，删除消息', '/admin/message/delete.do', '删除消息', '65', 'adminMessageDelete'), ('68', '后台，系统设置，用户注册后自动拥有的一个权限', '/admin/system/userRegRole.do', '注册用户权限', '15', 'adminSystemUserRegRole'), ('71', '后台，日志管理', '/admin/log/list.do', '日志管理', '0', 'adminLog'), ('72', '后台，日志管理，日志列表', '/admin/log/list.do', '日志列表', '71', 'adminLogList'), ('74', '管理后台－系统管理，新增、修改系统的全局变量', '/admin/system/variable.do', '修改全局变量', '15', 'adminSystemVariable'), ('75', '邀请注册页面，介绍说明页面', '/user/invite.do', '邀请注册页面', '1', 'userInvite'), ('77', '后台，论坛管理，帖子编辑、添加', '/admin/bbs/post.do', '添加修改帖子', '57', 'adminBbsPost'), ('78', '后台，论坛管理，删除帖子回复', '/admin/bbs/deleteComment.do', '删除回帖', '57', 'adminBbsDeletePostComment'), ('79', '后台，论坛管理，回帖列表', '/admin/bbs/commentList.do', '回帖列表', '57', 'adminBbsPostCommentList'), ('80', '后台，用户管理，查看用户详情', '/admin/user/view.do', '用户详情', '12', 'adminUserView'), ('81', '后台，用户管理，冻结、解除冻结会员', '/admin/user/updateFreeze.do', '冻结会员', '12', 'adminUserUpdateFreeze'), ('82', '后台，历史发送的短信验证码', '/admin/smslog/list.do', '验证码管理', '0', 'adminSmsLog'), ('83', '后台，历史发送的短信验证码列表', '/admin/smslog/list.do', '验证码列表', '82', 'adminSmsLogList'), ('86', '后台，在线支付记录', '/admin/payLog/list.do', '支付记录', '0', 'adminPayLog'), ('87', '后台，在线支付记录，记录列表', '/admin/payLog/list.do', '支付列表', '86', 'adminPayLogList'), ('88', '查看当前在线的会员', '', '在线会员', '12', 'adminOnlineUserList'), ('89', '后台管理首页，登录后台的话，需要授权此项，不然登录成功后仍然无法进入后台，被此页给拦截了', null, '管理后台', '0', 'adminIndex'), ('90', '管理后台首页', '', '后台首页', '89', 'adminIndexIndex'), ('116', '删除系统变量', 'admin/system/deleteVariable.do', '删除变量', '15', 'adminSystemDeleteVariable');
COMMIT;
-----
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `classid` int(11) unsigned NOT NULL COMMENT '发帖分类',
  `title` char(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '标题',
  `view` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '查看次数',
  `info` char(60) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '简介',
  `addtime` int(11) unsigned NOT NULL COMMENT '发布时间',
  `userid` int(11) unsigned NOT NULL COMMENT '发布用户',
  `state` tinyint(3) DEFAULT NULL COMMENT '状态，0:已删除，1:正常，2:审核中，3:审核完毕不符合要求，4:锁定冻结中，不允许回复',
  `isdelete` tinyint(2) DEFAULT NULL COMMENT '是否已经被删除。0正常，1已删除，',
  PRIMARY KEY (`id`),
  KEY `classid` (`classid`,`title`,`addtime`,`userid`,`state`,`isdelete`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='发帖,帖子主表';
-----
BEGIN;
INSERT INTO `post` VALUES ('21', '2', '阿萨德啊阿萨德啊d', '47', '<p>阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿', '1454038943', '1', '1', '0'), ('22', '1', 'c阿萨德啊好客户看哈时代', '15', '阿萨德哈会计师大会ask 家哈看似简单阿萨德撒阿萨德哈会计师大会ask 家哈看似简单阿萨德撒阿萨德哈会计师大会ask 家', '1455585050', '1', '1', '0');
COMMIT;
-----
DROP TABLE IF EXISTS `post_class`;
CREATE TABLE `post_class` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` char(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '分类名',
  `isdelete` tinyint(2) DEFAULT NULL COMMENT '是否已经被删除。0正常，1已删除，',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='论坛分类';
-----
BEGIN;
INSERT INTO `post_class` VALUES ('1', '一般论坛', '0'), ('2', '我要报bugs', '0'), ('3', '我要提需求', '0'), ('4', '常见问题', '0'), ('5', '功能更新', '0'), ('6', '使用教程', '0');
COMMIT;
-----
DROP TABLE IF EXISTS `post_comment`;
CREATE TABLE `post_comment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `postid` int(11) unsigned NOT NULL COMMENT 'post帖子的id',
  `addtime` int(11) unsigned NOT NULL COMMENT '发布时间',
  `userid` int(11) unsigned NOT NULL COMMENT '发布的用户',
  `text` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '回复的内容',
  `isdelete` tinyint(2) DEFAULT NULL COMMENT '是否已经被删除。0正常，1已删除，',
  PRIMARY KEY (`id`),
  KEY `postid` (`postid`,`addtime`,`userid`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='帖子回复';
-----
BEGIN;
INSERT INTO `post_comment` VALUES ('13', '22', '1453082658', '9', 'ewewe', '0'), ('14', '22', '1453082662', '9', 'www', '0'), ('19', '21', '1457169582', '27', 'sdf', '1');
COMMIT;
-----
DROP TABLE IF EXISTS `post_data`;
CREATE TABLE `post_data` (
  `postid` int(11) unsigned NOT NULL COMMENT 'post的id',
  `text` text COLLATE utf8_unicode_ci NOT NULL COMMENT '帖子内容',
  PRIMARY KEY (`postid`),
  KEY `postid` (`postid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='发帖，帖子主表的内容分表';
-----
BEGIN;
INSERT INTO `post_data` VALUES ('21', '<p>阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊阿萨德啊d</p>'), ('22', '阿萨德哈会计师大会ask 家哈看似简单阿萨德撒阿萨德哈会计师大会ask 家哈看似简单阿萨德撒阿萨德哈会计师大会ask 家哈看似简单阿萨德撒阿萨德哈会计师大会ask 家哈看似简单阿萨德撒阿萨德哈会计师大会ask 家哈看似简单阿萨德撒阿萨德哈会计师大会ask 家哈看似简单阿萨德撒阿萨德哈会计师大会ask 家哈看似简单阿萨德撒阿萨德哈会计师大会ask 家哈看似简单阿萨德撒');
COMMIT;
-----
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色名',
  `description` char(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色说明',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
-----
BEGIN;
INSERT INTO `role` VALUES ('1', '会员', '普通会员'), ('9', '超级管理员', '总后台管理');
COMMIT;
-----
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL COMMENT '角色id，role.id，一个角色可以拥有多个permission资源',
  `permissionid` int(11) DEFAULT NULL COMMENT '资源id，permission.id，一个角色可以拥有多个permission资源',
  PRIMARY KEY (`id`),
  KEY `roleid` (`roleid`)
) ENGINE=MyISAM AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 COMMENT='角色所拥有哪些资源的操作权限';
-----
BEGIN;
INSERT INTO `role_permission` VALUES ('1', '9', '7'), ('4', '9', '4'), ('5', '9', '3'), ('133', '1', '26'), ('132', '1', '25'), ('131', '1', '24'), ('130', '1', '23'), ('10', '1', '7'), ('129', '1', '11'), ('12', '9', '12'), ('13', '9', '13'), ('14', '9', '1'), ('15', '9', '2'), ('16', '9', '9'), ('17', '9', '15'), ('18', '9', '16'), ('19', '9', '17'), ('20', '9', '18'), ('21', '9', '19'), ('23', '9', '21'), ('24', '9', '22'), ('25', '9', '28'), ('27', '9', '30'), ('28', '9', '11'), ('29', '9', '23'), ('30', '9', '14'), ('138', '9', '27'), ('137', '9', '26'), ('136', '9', '25'), ('47', '9', '68'), ('135', '9', '24'), ('49', '9', '44'), ('51', '9', '46'), ('53', '9', '48'), ('54', '9', '49'), ('56', '9', '51'), ('58', '9', '53'), ('59', '9', '54'), ('60', '9', '55'), ('61', '9', '56'), ('62', '9', '57'), ('63', '9', '58'), ('64', '9', '59'), ('66', '9', '61'), ('69', '9', '64'), ('70', '9', '65'), ('71', '9', '66'), ('72', '9', '67'), ('75', '9', '71'), ('76', '9', '72'), ('77', '9', '74'), ('78', '9', '10'), ('79', '9', '20'), ('80', '9', '75'), ('81', '9', '29'), ('83', '9', '63'), ('84', '9', '77'), ('128', '1', '9'), ('127', '1', '30'), ('126', '1', '29'), ('125', '1', '3'), ('124', '1', '4'), ('123', '1', '75'), ('122', '1', '22'), ('121', '1', '21'), ('120', '1', '20'), ('119', '1', '19'), ('118', '1', '18'), ('117', '1', '2'), ('116', '1', '1'), ('115', '1', '10'), ('140', '9', '117'), ('114', '1', '28'), ('101', '9', '80'), ('102', '9', '78'), ('103', '9', '79'), ('104', '9', '81'), ('105', '9', '82'), ('106', '9', '83'), ('107', '9', '86'), ('108', '9', '87'), ('109', '9', '88'), ('110', '9', '89'), ('111', '9', '90'), ('112', '9', '116'), ('139', '13', '28');
COMMIT;
-----
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` char(6) CHARACTER SET utf8 DEFAULT NULL COMMENT '发送的验证码，6位数字',
  `userid` int(11) DEFAULT NULL COMMENT '使用此验证码的用户编号，user.id',
  `used` tinyint(2) DEFAULT '0' COMMENT '是否使用，0未使用，1已使用',
  `type` tinyint(3) DEFAULT NULL COMMENT '验证码所属功能类型，  1:登录  ； 2:找回密码',
  `addtime` int(11) DEFAULT NULL COMMENT '创建添加时间，linux时间戳10位',
  `phone` char(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '接收短信的手机号',
  `ip` char(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '触发发送操作的客户ip地址',
  PRIMARY KEY (`id`),
  KEY `code` (`code`,`userid`,`used`,`type`,`addtime`,`phone`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='短信验证发送的日志记录';
-----
BEGIN;
INSERT INTO `sms_log` VALUES ('2', '450132', '0', '0', '1', '1458378047', '17076012262', '0:0:0:0:0:0:0:1');
COMMIT;
-----
DROP TABLE IF EXISTS `system`;
CREATE TABLE `system` (
  `name` char(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '参数名,程序内调用',
  `description` char(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '说明描述',
  `value` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '值',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lasttime` int(11) DEFAULT '0' COMMENT '最后修改时间，10位时间戳',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='系统设置，系统的一些参数相关';
-----
BEGIN;
INSERT INTO `system` VALUES ('USER_REG_ROLE', '用户注册后的权限，其值对应角色 role.id', '1', '6', '1506333513'), ('SITE_NAME', '网站名称', 'iw', '7', null), ('SITE_KEYWORDS', '网站SEO搜索的关键字，首页根内页没有设置description的都默认用此', 'IW', '8', null), ('SITE_DESCRIPTION', '网站SEO描述，首页根内页没有设置description的都默认用此', '管雷鸣', '9', null), ('CURRENCY_NAME', '站内货币名字', '仙玉', '10', null), ('INVITEREG_AWARD_ONE', '邀请注册后奖励给邀请人多少站内货币（一级下线，直接推荐人，值必须为整数）', '5', '11', null), ('INVITEREG_AWARD_TWO', '邀请注册后奖励给邀请人多少站内货币（二级下线，值必须为整数）', '2', '12', null), ('INVITEREG_AWARD_THREE', '邀请注册后奖励给邀请人多少站内货币（三级下线，值必须为整数）', '1', '13', null), ('INVITEREG_AWARD_FOUR', '邀请注册后奖励给邀请人多少站内货币（四级下线，值必须为整数）', '1', '14', null), ('ROLE_USER_ID', '普通用户的角色id，其值对应角色 role.id', '1', '15', '1506333544'), ('ROLE_SUPERADMIN_ID', '超级管理员的角色id，其值对应角色 role.id', '9', '16', '1506333534'), ('BBS_DEFAULT_PUBLISH_CLASSID', '论坛中，如果帖子发布时，没有指明要发布到哪个论坛板块，那么默认选中哪个板块(分类)，这里便是分类的id，即数据表中的 post_class.id', '3', '20', '1506478724'), ('USER_HEAD_PATH', '用户头像(User.head)上传OSS或服务器进行存储的路径，存储于哪个文件夹中。<br/><b>注意</b><br/>1.这里最前面不要加/，最后要带/，如 head/<br/>2.使用中时，中途最好别改动，不然改动之前的用户设置好的头像就都没了', 'head/', '21', '1506481173'), ('ALLOW_USER_REG', '是否允许用户自行注册。<br/>1：允许用户自行注册<br/>0：禁止用户自行注册', '1', '22', '1507537911'), ('LIST_EVERYPAGE_NUMBER', '所有列表页面，每页显示的列表条数。', '15', '23', '1507538582');
COMMIT;
-----
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id编号',
  `username` char(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `email` char(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `password` char(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '加密后的密码',
  `head` char(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '头像,uuid+后缀',
  `nickname` char(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '姓名、昵称',
  `authority` char(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户权限,主要纪录表再user_role表，一个用户可以有多个权限。多个权限id用,分割，如2,3,5',
  `regtime` int(10) unsigned NOT NULL COMMENT '注册时间,时间戳',
  `lasttime` int(10) unsigned NOT NULL COMMENT '最后登录时间,时间戳',
  `regip` char(15) COLLATE utf8_unicode_ci NOT NULL COMMENT '注册ip',
  `salt` char(6) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'shiro加密使用',
  `phone` char(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号,11位',
  `currency` int(11) DEFAULT '0' COMMENT '资金，可以是积分、金币、等等站内虚拟货币',
  `referrerid` int(11) DEFAULT '0' COMMENT '推荐人的用户id。若没有推荐人则默认为0',
  `freezemoney` float(8,2) DEFAULT '0.00' COMMENT '账户冻结余额，金钱,RMB，单位：元',
  `lastip` char(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '最后一次登陆的ip',
  `isfreeze` tinyint(2) DEFAULT '0' COMMENT '是否已冻结，1已冻结（拉入黑名单），0正常',
  `money` float(8,2) DEFAULT '0.00' COMMENT '账户可用余额，金钱,RMB，单位：元',
  `idcardauth` tinyint(2) DEFAULT '0' COMMENT '是否已经经过真实身份认证了（身份证、银行卡绑定等）。默认为没有认证。预留字段。1已认证；0未认证',
  `sign` char(80) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '个人签名',
  `sex` char(4) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '男、女、未知',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`,`username`,`phone`) USING BTREE,
  KEY `username` (`username`,`email`,`phone`,`isfreeze`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户表';
-----
BEGIN;
INSERT INTO `user` VALUES ('1', 'admin', 'admin@xnx3.com', '674f4ee3b219cfc8040e79f67088e8e8', 'b36a693509724d9abb15c650d7f3b836.png', '23', '1,9', '1464340214', '1508491248', '127.0.0.1', '8850', null, '0', '0', '0.00', '127.0.0.1', '0', '0.00', '0', null, null), ('44', 'test', 'test@qq.com', '1126fc8d99bd8d3a7d67e4d6f1b75daa', '3ae55acf7883433895c53a7a7966a9f0.jpeg', 'test232', '1', '1507536932', '1507885007', '127.0.0.1', '6431', null, '0', '1', '0.00', '127.0.0.1', '0', '0.00', '0', null, null);
COMMIT;
-----
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '用户的id，user.id,一个用户可以有多个角色',
  `roleid` int(11) DEFAULT NULL COMMENT '角色的id，role.id ，一个用户可以有多个角色',
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='用户所属哪些角色';
-----
BEGIN;
INSERT INTO `user_role` VALUES ('27', '1', '1'), ('28', '1', '9'), ('35', '44', '1');
COMMIT;
-----
SET FOREIGN_KEY_CHECKS = 1;
