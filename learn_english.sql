-- ----------------------------
-- Table structure for t_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `t_knowledge`;
CREATE TABLE `t_knowledge`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `chinese` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `english` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` smallint(255) NULL DEFAULT NULL,
  `knowledge` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_user_id` bigint(20) NULL DEFAULT NULL,
  `update_user_id` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) COMMENT '知识点表';

-- ----------------------------
-- Table structure for t_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_login_log`;
CREATE TABLE `t_login_log`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `login_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `login_time` datetime(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) COMMENT '登陆日志表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) COMMENT '用户表';


insert into t_knowledge (`english`, `chinese`, `type`, `knowledge`, `create_user_id`) values ('I\'m taking the IELTS test because I want to study overseas.', '我要参加雅思考试，因为我想出国留学。', 2, '语法提示:现在遊行时，状语从句', 1),('There\'s a population of 21 million in Bejing, so it\'s jam-packed wherever you go.', '北京有2100万人日，所以无论你走到哪里都挤满了人。', 2, '语法提示:there be 句型， 一般现在时， 因果关系', 1),('I was born and raised in a small town in the south of China and I have two loving and caring parents.', '我在中国南方的一个小镇出生长大，我有一对关爱我的父母。', 2, '语法提示:一般过去时，并列句， 一般现在时', 1),('Some of my friends are aiming to go and study in Australia, whereas my goal is to got o the UK.', '我的一些朋友打算去澳大利亚学习，然而我的目标是去英国。', 2, '语法提示:现在进行时，对比，一般现在时', 1),('The Mid-Autumn Festival falls on the 15th day of the eighth month of the lunar calendar.', '中秋节是阴历第八个月的第十五天', 2, '语法提示 : 简单句，一般现在时', 1),('It\'s usually scorching hot in my hometown in the summertime. The temperature can even be 40 degrees Celsius sometimes.', '在夏季，我家乡的天气通常是炎热的，有时气温甚至可以达到40摄氏度。', 2, '语法提示:简单句， 一般现在时， 情态动词', 1),('I was amazed at all the vivid colours while I was snorkelling and looking down at the underwater world.', '当我在浮潜，俯视着海底世界的时候，所有艳丽的色彩令我惊叹。', 2, '语法提示:一般过去时，状语从句(while的用法)， 过去进行时', 1),('It\'s highly likely that we will use the computer more and more and the pen less and less in the future.', '将来我们很有可能越来越多地使用电脑，越来越少地使用笔。', 2, '语法提示: 主语从句， more and more 的用法 ， 一 般现在时， 一般将来时', 1),('I don\'t suppose shared bicycles are the best way to reduce traffic congestion.', '我不认为共享单车是减少交通拥堵的最佳方式。', 2, '语法提示: 一般现在时，宾语从句', 1),('The government is investing heavily in electric buses and I\'m totally on their side.', '政府正大力投资于电动公共汽车，我完全支持他们。', 2, '语法提示:现在进行时， 并列句', 1),('I wish I could speak English better. That way, I would be able to travel the world without any difficulty.', '我希望我的英语能说得更好。那样的话，我就可以毫无困难地周游世界了。', 2, '语法提示: 虛拟语气', 1),('I have a penchant for cooking and I seldom eat out because I\'m only satisfied with the food I cook.', '我爱好烹饪，很少出去吃，因为我只对自己做的菜感到满意。', 2, '语法提示:并列句，状语从句， 一般现在时', 1),('My granddad is already in his late 70s but he is still as fit as a fiddle because he eats well and takes exercise every day.', '我爷爷已经快八十发了，但他仍然很健康，因为他吃得好，而且每天银炼。', 2, '语法提示:转折，状语从句， 一般现在时', 1),('Many people from the countryside and small towns flock to large cities in search of a better job and a better future.', '许多来自农村和小城镇的人涌向大城市，为了寻找更好的工作和更好的未来。', 2, '语法提示:简单句，比较级， 一般现在时', 1),('Although some areas are unaffected by climate change now, it\'s a pressing problem that has to be dealt with immediately.', '虽然有些地区还没有受到气候变化的影响，但这是一个迫切需要立即解决的问题。', 2, '语法提示:状语从句，定语从句，一般现在时，被动语态', 1),('Celebrities are constantly followed by paparazzi and worried about being recognised on the street when they are on vacation and just want to relax.', '名人经常会被狗仔队跟踪，他们担心在度假和只是想要放松的时候，在街上被认出来。', 2, '语法提示:状语从句，被动语态，一般现在时', 1),('I\'m an Aquarius and it\'s said that people like me are born thinkers.', '我是水瓶座，据说像我这样的人，天生就是思考者。', 2, '语法提示:an 的使用，一般現在时，并列句，主语从句', 1),('One of my cousins is crazy about travelling and she has been to dozens of countries over the past 10 years.', '我的一个表妹热衷于旅游，过去10年来，她已经去过几十个国家。', 2, '语法提示:并列句，现在完成时', 1),('If this park were closer to my apartment, I guess I would go there more often.', '如果这个公园离我的公寓更近一些，我想我会更经常地去那里。', 2, '语法捉示:状语从向，此较级，实语从句，虚拟语气', 1),('Fair-weather friends are definitely the kind of friends we don\'t want.', '不能共患难的朋友绝对是我们不想要的那种朋友。', 2, '语法捉示:定语从句， 一般现在时', 1),('Smartphones have blurred the lines between work and home lives and we often communicate with our colleagues and bosses after work now.', '智能手机機糊了工作和家庭生活之间的界限，现在即使下了班，我们也经常和同事还有老板沟通。', 2, '语法提示: 并列句，现在完成时，一般现在时', 1),('It\'s okay if teenagers play video games in moderation. All work and no play makes Jack a dull boy.', '如果青少年适度地玩电子游戏，这没关系。只会用功不玩耍，聪明孩子也变傻。', 2, '语法提示:状语从句，简单句， 一般现在时', 1),('It\'s obvious that being multilingual gives people an advantage in the workplace.', '很明显，掌握多门语言能让人们在工作中占有优势。', 2, '语法禔示: 主语从句， 一般现在时', 1),('Word-of-mouth advertising is the most effective form of advertising.', '口碑传播是最有效的广告形式。', 2, '语法提示:简单句，最高级， 一般现在时', 1),('Looking at old photos brings back memories.', '看老照片会勾起回忆。', 2, '语法提示:v. + ing 的用法，一般现在时', 1),('I went to the biggest theatre in town for a fascinating play last weekend.', '上周末，我去城里最大的剧院，看了一场精彩的演出。', 2, '语法提示:一般过去时， 最高级', 1),('The bus is the cheapest way to get around even though it\'s overcrowded most of the time.', '公共汽车是最便宜的出行方式，尽管大部分时间都很拥挤。', 2, '语法提示:一般现在时，最高级，状语从句', 1),('Success comes in various forms. It could be having a high social status, earning a seven-figure salary or simply feeling happy every day.', '成功有多种多样的形式，它可以是拥有很高的社会地位，赚七位数的工资或单纯觉得每天都很快乐。', 2, '语法提示:情态动词，一般现在时，v. + ing的用法', 1),('It\'s best to get away from it all and go on an excursion on the weekend.', '在周末出去走走、短途路行是很好的。', 2, '语法提示: 不定式作主语，一般现在时', 1),('Spending money on experiences, especially with other people, is what makes us truly happy.', '把钱花在经历上，特別是和别人一起的经历，可以让我们真正获得快乐。', 2, '语法提示:v. + ing 的用法， 一般现在时', 1);