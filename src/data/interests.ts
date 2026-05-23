import type { InterestCategory, InterestTag } from '../types';

export const INTEREST_CATEGORIES: InterestCategory[] = [
  { id: 'TECH', name: '信息技术', icon: '💻' },
  { id: 'ENGINEERING', name: '工程技术', icon: '🔧' },
  { id: 'SCIENCE', name: '自然科学', icon: '🔬' },
  { id: 'MEDICINE', name: '医药健康', icon: '🏥' },
  { id: 'ECONOMICS', name: '经济管理', icon: '📊' },
  { id: 'ARTS', name: '人文艺术', icon: '🎨' },
  { id: 'LAW', name: '法学政治', icon: '⚖️' },
  { id: 'EDUCATION', name: '教育心理', icon: '📚' },
  { id: 'AGRICULTURE', name: '农林食品', icon: '🌱' },
  { id: 'OTHERS', name: '其他方向', icon: '🌟' }
];

export const INTEREST_TAGS: InterestTag[] = [
  // 信息技术
  { id: 'AI', name: '人工智能', categoryId: 'TECH', relatedMajorTags: ['AI', '人工智能', '智能'] },
  { id: 'ML', name: '机器学习', categoryId: 'TECH', relatedMajorTags: ['机器学习', 'AI', '智能'] },
  { id: 'DATA_SCIENCE', name: '数据科学', categoryId: 'TECH', relatedMajorTags: ['数据科学', '统计学', '数据'] },
  { id: 'CS', name: '计算机科学', categoryId: 'TECH', relatedMajorTags: ['计算机', '软件', 'CS'] },
  { id: 'SOFTWARE_ENG', name: '软件工程', categoryId: 'TECH', relatedMajorTags: ['软件工程', '软件', '程序'] },
  { id: 'CYBER_SECURITY', name: '网络安全', categoryId: 'TECH', relatedMajorTags: ['网络安全', '信息安全', '安全'] },
  { id: 'CS_THEORY', name: '计算机理论', categoryId: 'TECH', relatedMajorTags: ['计算机', '计算'] },
  { id: 'GRAPHICS', name: '计算机图形学', categoryId: 'TECH', relatedMajorTags: ['图形学', '视觉', '动画'] },
  { id: 'HCI', name: '人机交互', categoryId: 'TECH', relatedMajorTags: ['交互', '设计'] },
  { id: 'ROBOTICS', name: '机器人', categoryId: 'TECH', relatedMajorTags: ['机器人', '自动化'] },
  
  // 工程技术
  { id: 'AUTOMATION', name: '自动化', categoryId: 'ENGINEERING', relatedMajorTags: ['自动化', '控制'] },
  { id: 'EE', name: '电子工程', categoryId: 'ENGINEERING', relatedMajorTags: ['电子', '电气', 'EE'] },
  { id: 'ME', name: '机械工程', categoryId: 'ENGINEERING', relatedMajorTags: ['机械', '制造'] },
  { id: 'CE', name: '土木工程', categoryId: 'ENGINEERING', relatedMajorTags: ['土木', '建筑', '工程'] },
  { id: 'AE', name: '航空航天', categoryId: 'ENGINEERING', relatedMajorTags: ['航空', '航天', '飞行器'] },
  { id: 'CHEM_ENG', name: '化学工程', categoryId: 'ENGINEERING', relatedMajorTags: ['化工', '化学工程'] },
  { id: 'MATERIALS', name: '材料科学', categoryId: 'ENGINEERING', relatedMajorTags: ['材料', '材料科学'] },
  { id: 'NUCLEAR', name: '核工程', categoryId: 'ENGINEERING', relatedMajorTags: ['核工程', '核技术'] },
  { id: 'BIOMED_ENG', name: '生物医学工程', categoryId: 'ENGINEERING', relatedMajorTags: ['生物医学', '医学工程'] },
  { id: 'ENV_ENG', name: '环境工程', categoryId: 'ENGINEERING', relatedMajorTags: ['环境工程', '环保'] },
  { id: 'ARCHITECTURE', name: '建筑学', categoryId: 'ENGINEERING', relatedMajorTags: ['建筑', '设计'] },
  
  // 自然科学
  { id: 'MATH', name: '数学', categoryId: 'SCIENCE', relatedMajorTags: ['数学', '统计'] },
  { id: 'PHYSICS', name: '物理学', categoryId: 'SCIENCE', relatedMajorTags: ['物理', '应用物理'] },
  { id: 'CHEMISTRY', name: '化学', categoryId: 'SCIENCE', relatedMajorTags: ['化学', '应用化学'] },
  { id: 'BIOLOGY', name: '生物学', categoryId: 'SCIENCE', relatedMajorTags: ['生物', '生命科学'] },
  { id: 'EARTH_SCI', name: '地球科学', categoryId: 'SCIENCE', relatedMajorTags: ['地球', '地质', '地理'] },
  { id: 'ASTRONOMY', name: '天文学', categoryId: 'SCIENCE', relatedMajorTags: ['天文', '天体'] },
  { id: 'STATISTICS', name: '统计学', categoryId: 'SCIENCE', relatedMajorTags: ['统计', '应用统计'] },
  { id: 'PSYCHOLOGY_SCI', name: '心理学', categoryId: 'SCIENCE', relatedMajorTags: ['心理学', '认知'] },
  
  // 医药健康
  { id: 'CLINICAL', name: '临床医学', categoryId: 'MEDICINE', relatedMajorTags: ['临床', '医学'] },
  { id: 'PHARMACY', name: '药学', categoryId: 'MEDICINE', relatedMajorTags: ['药学', '药剂'] },
  { id: 'DENTISTRY', name: '口腔医学', categoryId: 'MEDICINE', relatedMajorTags: ['口腔', '牙科'] },
  { id: 'PUBLIC_HEALTH', name: '公共卫生', categoryId: 'MEDICINE', relatedMajorTags: ['公共卫生', '预防'] },
  { id: 'NURSING', name: '护理学', categoryId: 'MEDICINE', relatedMajorTags: ['护理', '护士'] },
  { id: 'MED_IMAGING', name: '医学影像', categoryId: 'MEDICINE', relatedMajorTags: ['医学影像', '影像'] },
  { id: 'TCM', name: '中医学', categoryId: 'MEDICINE', relatedMajorTags: ['中医', '中药'] },
  
  // 经济管理
  { id: 'FINANCE', name: '金融学', categoryId: 'ECONOMICS', relatedMajorTags: ['金融', '投资'] },
  { id: 'ECON', name: '经济学', categoryId: 'ECONOMICS', relatedMajorTags: ['经济', '经济学'] },
  { id: 'ACCOUNTING', name: '会计学', categoryId: 'ECONOMICS', relatedMajorTags: ['会计', '财务'] },
  { id: 'BUSINESS', name: '工商管理', categoryId: 'ECONOMICS', relatedMajorTags: ['工商', '管理', '企业'] },
  { id: 'MARKETING', name: '市场营销', categoryId: 'ECONOMICS', relatedMajorTags: ['营销', '市场'] },
  { id: 'INTERNATIONAL_TRADE', name: '国际贸易', categoryId: 'ECONOMICS', relatedMajorTags: ['国际贸易', '国际商务'] },
  { id: 'HR', name: '人力资源', categoryId: 'ECONOMICS', relatedMajorTags: ['人力资源', 'HR'] },
  { id: 'LOGISTICS', name: '物流管理', categoryId: 'ECONOMICS', relatedMajorTags: ['物流', '供应链'] },
  
  // 人文艺术
  { id: 'LITERATURE', name: '文学', categoryId: 'ARTS', relatedMajorTags: ['文学', '中文', '汉语言'] },
  { id: 'HISTORY', name: '历史学', categoryId: 'ARTS', relatedMajorTags: ['历史', '史学'] },
  { id: 'PHILOSOPHY', name: '哲学', categoryId: 'ARTS', relatedMajorTags: ['哲学', '思想'] },
  { id: 'LANGUAGES', name: '外语', categoryId: 'ARTS', relatedMajorTags: ['外语', '英语', '翻译'] },
  { id: 'JOURNALISM', name: '新闻传播', categoryId: 'ARTS', relatedMajorTags: ['新闻', '传播', '媒体'] },
  { id: 'DESIGN', name: '设计学', categoryId: 'ARTS', relatedMajorTags: ['设计', '艺术设计'] },
  { id: 'FINE_ARTS', name: '美术', categoryId: 'ARTS', relatedMajorTags: ['美术', '绘画'] },
  { id: 'MUSIC', name: '音乐', categoryId: 'ARTS', relatedMajorTags: ['音乐', '表演'] },
  { id: 'FILM', name: '影视', categoryId: 'ARTS', relatedMajorTags: ['影视', '戏剧', '导演'] },
  
  // 法学政治
  { id: 'LAW_MAJOR', name: '法学', categoryId: 'LAW', relatedMajorTags: ['法学', '法律'] },
  { id: 'POLITICS', name: '政治学', categoryId: 'LAW', relatedMajorTags: ['政治', '行政'] },
  { id: 'PUBLIC_ADMIN', name: '公共管理', categoryId: 'LAW', relatedMajorTags: ['公共管理', '行政管理'] },
  { id: 'SOCIOLOGY', name: '社会学', categoryId: 'LAW', relatedMajorTags: ['社会学', '社会'] },
  { id: 'INTERNATIONAL_RELATIONS', name: '国际关系', categoryId: 'LAW', relatedMajorTags: ['国际', '外交'] },
  
  // 教育心理
  { id: 'EDU', name: '教育学', categoryId: 'EDUCATION', relatedMajorTags: ['教育', '师范'] },
  { id: 'PSYCHOLOGY', name: '心理学', categoryId: 'EDUCATION', relatedMajorTags: ['心理学', '心理'] },
  { id: 'SPECIAL_EDU', name: '特殊教育', categoryId: 'EDUCATION', relatedMajorTags: ['特殊教育'] },
  { id: 'PRESCHOOL', name: '学前教育', categoryId: 'EDUCATION', relatedMajorTags: ['学前', '幼儿'] },
  
  // 农林食品
  { id: 'AGRONOMY', name: '农学', categoryId: 'AGRICULTURE', relatedMajorTags: ['农学', '农业'] },
  { id: 'FORESTRY', name: '林学', categoryId: 'AGRICULTURE', relatedMajorTags: ['林学', '林业'] },
  { id: 'FOOD_SCI', name: '食品科学', categoryId: 'AGRICULTURE', relatedMajorTags: ['食品', '营养'] },
  { id: 'ANIMAL_SCI', name: '动物科学', categoryId: 'AGRICULTURE', relatedMajorTags: ['动物', '兽医'] },
  { id: 'VETERINARY', name: '兽医学', categoryId: 'AGRICULTURE', relatedMajorTags: ['兽医', '动物医学'] },
  
  // 其他方向
  { id: 'MILITARY', name: '军事', categoryId: 'OTHERS', relatedMajorTags: ['军事', '国防'] },
  { id: 'SPORTS', name: '体育', categoryId: 'OTHERS', relatedMajorTags: ['体育', '运动'] },
  { id: 'FASHION', name: '服装时尚', categoryId: 'OTHERS', relatedMajorTags: ['服装', '时尚'] },
  { id: 'TOURISM', name: '旅游管理', categoryId: 'OTHERS', relatedMajorTags: ['旅游', '酒店管理'] }
];
