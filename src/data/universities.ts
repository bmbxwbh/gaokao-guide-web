import type { University } from '../types';

export const universities: University[] = [
  {
    id: 'sichuan-normal-university',
    name: '四川师范大学',
    shortName: '川师大',
    logo: '/images/sichuan-normal-university/logo.png',
    type: 'NORMAL',
    location: {
      district: '锦江区',
      city: '成都市',
      province: '四川省'
    },
    address: '成都市锦江区静安路5号',
    phone: '028-84760708',
    website: 'https://www.sicnu.edu.cn',
    description: '四川师范大学是四川省属重点大学，创建于1946年，是国家中西部高校基础能力建设工程重点建设高校。学校以师范教育为特色，多学科协调发展，拥有完整的学士、硕士、博士人才培养体系。',
    foundingYear: 1946,
    department: '四川省教育厅',
    keyDisciplines: ['教育学', '中国语言文学', '数学', '物理学', '化学'],
    images: [],
    majors: [
      {
        id: 'snu-chinese',
        name: '汉语言文学',
        code: '050101',
        department: '文学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'LITERATURE',
        introduction: '汉语言文学是四川师范大学历史最悠久、实力最雄厚的专业之一，主要培养具有扎实的文学理论素养和系统的汉语言文学知识的人才。',
        trainingGoal: '培养德智体美劳全面发展，具有扎实的汉语言文学基本理论、基础知识和基本技能的高级专门人才。',
        mainCourses: ['文学概论', '中国古代文学', '中国现代文学', '中国当代文学', '外国文学', '美学', '比较文学'],
        employmentDirections: ['中小学语文教师', '新闻媒体编辑', '企事业单位文秘', '公务员', '企业宣传'],
        furtherStudyDirections: ['中国语言文学', '比较文学与世界文学', '文艺学', '语言学及应用语言学'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'LIBERAL_ARTS',
            batch: 'FIRST',
            lowScore: 562,
            avgScore: 568,
            highScore: 580,
            provinceControlLine: 527,
            planCount: 60,
            actualCount: 62
          },
          '2024': {
            subjectType: 'LIBERAL_ARTS',
            batch: 'FIRST',
            lowScore: 558,
            avgScore: 564,
            highScore: 576,
            provinceControlLine: 525
          }
        }
      },
        {
        id: 'snu-math',
        name: '数学与应用数学',
        code: '070101',
        department: '数学科学学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '数学与应用数学专业是我校的传统优势专业，师资力量雄厚，教学成果丰硕。',
        trainingGoal: '培养掌握数学科学的基本理论与基本方法，具有运用数学知识、使用计算机解决实际问题的能力。',
        mainCourses: ['数学分析', '高等代数', '解析几何', '概率论', '实变函数', '常微分方程', '复变函数', '泛函分析'],
        employmentDirections: ['中小学数学教师', '金融分析师', '软件工程师', '数据分析师', '科研人员'],
        furtherStudyDirections: ['基础数学', '应用数学', '计算数学', '概率论与数理统计'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 568,
            avgScore: 575,
            highScore: 590,
            provinceControlLine: 520,
            planCount: 50,
            actualCount: 51
          }
        }
      }
    ],
    overviewScores: {
      '2025': {
        scienceLow: 560,
        scienceAvg: 575,
        scienceHigh: 610,
        liberalArtsLow: 550,
        liberalArtsAvg: 565,
        liberalArtsHigh: 590
      }
    }
  },
  {
    id: 'chengdu-university-of-tcm',
    name: '成都中医药大学',
    shortName: '成中医',
    logo: '/images/chengdu-university-of-tcm/logo.png',
    type: 'MEDICAL',
    location: {
      district: '温江区',
      city: '成都市',
      province: '四川省'
    },
    address: '成都市温江区柳台大道1166号',
    phone: '028-61800000',
    website: 'https://www.cdutcm.edu.cn',
    description: '成都中医药大学是一所以中医药学科为主体，医药健康及相关学科协调发展的中医药大学。学校创建于1956年，是新中国最早成立的四所中医药院校之一。',
    foundingYear: 1956,
    department: '四川省教育厅',
    keyDisciplines: ['中医学', '中药学', '针灸推拿学', '中西医结合', '药学'],
    images: [],
    majors: [
      {
        id: 'cdutcm-tcm',
        name: '中医学',
        code: '100501K',
        department: '基础医学院',
        duration: 5,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '中医学专业是成都中医药大学的王牌专业，是国家特色专业，历史悠久，师资力量雄厚。',
        trainingGoal: '培养具备中医药理论基础、中医学专业知识和专业实践技能的高级中医医师。',
        mainCourses: ['中医基础理论', '中医诊断学', '中药学', '方剂学', '中医内科学', '中医外科学', '针灸学'],
        employmentDirections: ['中医院医师', '综合医院中医科医师', '社区卫生服务中心', '医药企业', '科研机构'],
        furtherStudyDirections: ['中医内科学', '中医外科学', '中医妇科学', '针灸推拿学'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 572,
            avgScore: 580,
            highScore: 600,
            provinceControlLine: 520,
            planCount: 80,
            actualCount: 82
          }
        }
      },
      {
        id: 'cdutcm-pharmacy',
        name: '中药学',
        code: '100801',
        department: '药学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '中药学专业培养从事中药鉴定、中药炮制、中药制剂、中药质量分析等方面的高级专门人才。',
        trainingGoal: '培养具备中药学基础理论、基本知识、基本技能的高级专门人才。',
        mainCourses: ['中药学', '方剂学', '药用植物学', '中药鉴定学', '中药药剂学', '中药药理学'],
        employmentDirections: ['医院药房', '药品生产企业', '药品检验所', '医药公司', '科研机构'],
        furtherStudyDirections: ['中药学', '生药学', '药剂学', '药物分析学'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 565,
            avgScore: 572,
            highScore: 590,
            provinceControlLine: 520,
            planCount: 70,
            actualCount: 72
          }
        }
      }
    ],
    overviewScores: {
      '2025': {
        scienceLow: 565,
        scienceAvg: 580,
        scienceHigh: 620,
        liberalArtsLow: 545,
        liberalArtsAvg: 560,
        liberalArtsHigh: 585
      }
    }
  },
  {
    id: 'chengdu-university-of-technology',
    name: '成都理工大学',
    shortName: '成理',
    logo: '/images/chengdu-university-of-technology/logo.png',
    type: 'SCIENCE',
    location: {
      district: '成华区',
      city: '成都市',
      province: '四川省'
    },
    address: '成都市成华区二仙桥东三路1号',
    phone: '028-84078927',
    website: 'https://www.cdut.edu.cn',
    description: '成都理工大学是一所以理工为主，以地质、能源、资源科学、环境科学为特色的综合性大学。学校是国家“双一流”建设高校，以地球科学为优势学科。',
    foundingYear: 1956,
    department: '四川省教育厅',
    keyDisciplines: ['地质学', '石油工程', '土木工程', '环境科学与工程', '资源勘查工程'],
    images: [],
    majors: [
      {
        id: 'cdut-geology',
        name: '地质学',
        code: '070901',
        department: '地球科学学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '地质学专业是成都理工大学的王牌专业，是国家重点学科，在国内享有盛誉。',
        trainingGoal: '培养具备地质学基础理论、基本知识和基本技能的高级专门人才。',
        mainCourses: ['普通地质学', '结晶学与矿物学', '岩石学', '构造地质学', '古生物学', '地史学'],
        employmentDirections: ['地质调查院', '矿产勘查公司', '石油公司', '科研院所', '高校'],
        furtherStudyDirections: ['矿物学、岩石学、矿床学', '构造地质学', '古生物学与地层学'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 578,
            avgScore: 588,
            highScore: 610,
            provinceControlLine: 520,
            planCount: 45,
            actualCount: 47
          }
        }
      },
      {
        id: 'cdut-civil',
        name: '土木工程',
        code: '081001',
        department: '环境与土木工程学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '土木工程专业培养掌握各类土木工程学科的基本理论和基本知识的高级工程技术人才。',
        trainingGoal: '培养具备从事土木工程的项目规划、设计、研究开发、施工及管理能力的人才。',
        mainCourses: ['材料力学', '结构力学', '工程地质学', '土力学', '混凝土结构', '钢结构'],
        employmentDirections: ['建筑设计院', '建筑施工企业', '房地产公司', '政府建设管理部门', '监理公司'],
        furtherStudyDirections: ['结构工程', '岩土工程', '防灾减灾工程', '桥梁与隧道工程'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 572,
            avgScore: 580,
            highScore: 598,
            provinceControlLine: 520,
            planCount: 90,
            actualCount: 92
          }
        }
      }
    ],
    overviewScores: {
      '2025': {
        scienceLow: 570,
        scienceAvg: 585,
        scienceHigh: 625,
        liberalArtsLow: 540,
        liberalArtsAvg: 555,
        liberalArtsHigh: 580
      }
    }
  },
  {
    id: 'chengdu-information-technology',
    name: '成都信息工程大学',
    shortName: '成信大',
    logo: '/images/chengdu-information-technology/logo.png',
    type: 'SCIENCE',
    location: {
      district: '双流区',
      city: '成都市',
      province: '四川省'
    },
    address: '成都市双流区航空港学府路一段24号',
    phone: '028-85966000',
    website: 'https://www.cuit.edu.cn',
    description: '成都信息工程大学是一所以信息学科和大气学科为重点，多学科协调发展的教学研究型大学。学校在气象、电子信息等领域具有鲜明特色。',
    foundingYear: 1951,
    department: '四川省教育厅',
    keyDisciplines: ['气象学', '电子信息工程', '计算机科学与技术', '通信工程', '软件工程'],
    images: [],
    majors: [
      {
        id: 'cuit-meteorology',
        name: '大气科学',
        code: '070601',
        department: '大气科学学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '大气科学专业是成都信息工程大学的王牌专业，是国家级特色专业。',
        trainingGoal: '培养具有扎实的大气科学基本理论、基本知识和基本技能的高级专门人才。',
        mainCourses: ['大气科学概论', '天气学原理', '动力气象学', '大气物理学', '天气分析', '气候学'],
        employmentDirections: ['气象局', '民航气象中心', '科研院所', '民航机场', '航空公司'],
        furtherStudyDirections: ['气象学', '大气物理学与大气环境', '大气遥感科学与技术'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 555,
            avgScore: 565,
            highScore: 585,
            provinceControlLine: 520,
            planCount: 40,
            actualCount: 42
          }
        }
      },
      {
        id: 'cuit-electronic',
        name: '电子信息工程',
        code: '080701',
        department: '电子工程学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '电子信息工程专业培养具备电子技术和信息系统的基础知识的高级工程技术人才。',
        trainingGoal: '培养具备电子技术和信息系统的基础知识，能从事各类电子设备和信息系统的研究、设计、制造的人才。',
        mainCourses: ['电路分析', '模拟电子技术', '数字电子技术', '信号与系统', '通信原理'],
        employmentDirections: ['电子信息企业', '通信设备制造商', '科研院所', 'IT公司', '电信运营商'],
        furtherStudyDirections: ['电路与系统', '信号与信息处理', '通信与信息系统'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 550,
            avgScore: 560,
            highScore: 578,
            provinceControlLine: 520,
            planCount: 80,
            actualCount: 82
          }
        }
      }
    ],
    overviewScores: {
      '2025': {
        scienceLow: 548,
        scienceAvg: 563,
        scienceHigh: 598,
        liberalArtsLow: 528,
        liberalArtsAvg: 543,
        liberalArtsHigh: 568
      }
    }
  },
  {
    id: 'chengdu-university',
    name: '成都大学',
    shortName: '成大',
    logo: '/images/chengdu-university/logo.png',
    type: 'COMPREHENSIVE',
    location: {
      district: '龙泉驿区',
      city: '成都市',
      province: '四川省'
    },
    address: '成都市龙泉驿区十陵镇上街1号',
    phone: '028-84616000',
    website: 'https://www.cdu.edu.cn',
    description: '成都大学是一所综合性大学，创建于1978年，是四川省和成都市共建的重点大学。学校以工学、文学、管理学、经济学、法学、教育学、文学、艺术学等多学科协调发展。',
    foundingYear: 1978,
    department: '成都市人民政府',
    keyDisciplines: ['生物医学工程', '食品科学与工程', '旅游管理', '建筑学', '设计学'],
    images: [],
    majors: [
      {
        id: 'cdu-food',
        name: '食品科学与工程',
        code: '082701',
        department: '食品与生物工程学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '食品科学与工程专业是成都大学的重点建设专业，具有鲜明的地方特色。',
        trainingGoal: '培养具有化学、生物学、食品工程和食品技术知识的高级工程技术人才。',
        mainCourses: ['食品化学', '食品微生物学', '食品工程原理', '食品工艺学', '食品营养学'],
        employmentDirections: ['食品生产企业', '食品检验机构', '科研院所', '质量监督部门', '餐饮企业'],
        furtherStudyDirections: ['食品科学', '农产品加工及贮藏工程', '粮食、油脂及植物蛋白工程'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 548,
            avgScore: 558,
            highScore: 578,
            provinceControlLine: 520,
            planCount: 55,
            actualCount: 56
          }
        }
      }
    ],
    overviewScores: {
      '2025': {
        scienceLow: 545,
        scienceAvg: 560,
        scienceHigh: 595,
        liberalArtsLow: 525,
        liberalArtsAvg: 540,
        liberalArtsHigh: 565
      }
    }
  },
  {
    id: 'xihua-university',
    name: '西华大学',
    shortName: '西华',
    logo: '/images/xihua-university/logo.png',
    type: 'SCIENCE',
    location: {
      district: '郫都区',
      city: '成都市',
      province: '四川省'
    },
    address: '成都市郫都区红光镇广场路二段189号',
    phone: '028-87720000',
    website: 'https://www.xhu.edu.cn',
    description: '西华大学是一所以工为主，理、工、管、文、经、法、艺协调发展的综合性大学。学校是四川省属重点综合性大学，具有悠久的办学历史。',
    foundingYear: 1960,
    department: '四川省教育厅',
    keyDisciplines: ['流体机械及工程', '食品科学与工程', '计算机科学与技术', '机械工程', '车辆工程'],
    images: [],
    majors: [
      {
        id: 'xhu-mechanical',
        name: '机械电子工程',
        code: '080204',
        department: '机械工程学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '机械电子工程专业是西华大学的优势专业，将机械技术、电子技术、计算机技术有机结合。',
        trainingGoal: '培养具备机械电子工程基础知识与应用能力的高级工程技术人才。',
        mainCourses: ['工程力学', '机械设计', '电子技术', '液压与气压传动', '机电传动控制'],
        employmentDirections: ['机械制造企业', '汽车制造企业', '电子设备企业', '科研院所', '机电设备公司'],
        furtherStudyDirections: ['机械电子工程', '机械制造及其自动化', '机械设计及理论'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 545,
            avgScore: 555,
            highScore: 575,
            provinceControlLine: 520,
            planCount: 75,
            actualCount: 77
          }
        }
      },
      {
        id: 'xhu-vehicle',
        name: '车辆工程',
        code: '080207',
        department: '汽车与交通学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '车辆工程专业培养掌握汽车设计、制造、试验等方面知识的高级工程技术人才。',
        trainingGoal: '培养具备车辆工程基础知识与应用能力的高级工程技术人才。',
        mainCourses: ['汽车构造', '汽车理论', '汽车设计', '汽车制造工艺学', '汽车试验学'],
        employmentDirections: ['汽车制造企业', '汽车零部件企业', '汽车研发中心', '科研院所', '汽车检测机构'],
        furtherStudyDirections: ['车辆工程', '动力机械及工程'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 550,
            avgScore: 560,
            highScore: 580,
            provinceControlLine: 520,
            planCount: 80,
            actualCount: 82
          }
        }
      }
    ],
    overviewScores: {
      '2025': {
        scienceLow: 540,
        scienceAvg: 555,
        scienceHigh: 590,
        liberalArtsLow: 520,
        liberalArtsAvg: 535,
        liberalArtsHigh: 560
      }
    }
  },
  {
    id: 'southwest-minzu-university',
    name: '西南民族大学',
    shortName: '西南民大',
    logo: '/images/southwest-minzu-university/logo.png',
    type: 'COMPREHENSIVE',
    location: {
      district: '武侯区',
      city: '成都市',
      province: '四川省'
    },
    address: '成都市武侯区一环路南四段16号',
    phone: '028-85522000',
    website: 'https://www.swun.edu.cn',
    description: '西南民族大学是一所综合性民族高校，以民族学、畜牧兽医学、艺术学为特色的多学科协调发展的大学。学校创建于1951年，是新中国最早成立的民族院校之一。',
    foundingYear: 1951,
    department: '国家民族事务委员会',
    keyDisciplines: ['民族学', '畜牧兽医学', '中国语言文学', '艺术学', '法学'],
    images: [],
    majors: [
      {
        id: 'swun-ethnology',
        name: '民族学',
        code: '030401',
        department: '民族学与社会学学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'LITERATURE',
        introduction: '民族学专业是西南民族大学的特色专业，培养从事民族研究、文化工作的高级专门人才。',
        trainingGoal: '培养具备民族学基本理论、知识和技能的高级专门人才。',
        mainCourses: ['民族学概论', '中国民族史', '民族理论与民族政策', '文化人类学'],
        employmentDirections: ['民族事务部门', '科研院所', '文化部门', '新闻媒体', '企事业单位'],
        furtherStudyDirections: ['民族学', '人类学', '社会学', '民俗学'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'LIBERAL_ARTS',
            batch: 'FIRST',
            lowScore: 540,
            avgScore: 548,
            highScore: 565,
            provinceControlLine: 527,
            planCount: 30,
            actualCount: 32
          }
        }
      },
      {
        id: 'swun-veterinary',
        name: '动物医学',
        code: '090401',
        department: '生命科学与技术学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '动物医学专业是西南民族大学的传统优势专业，培养从事动物疾病诊疗的高级专门人才。',
        trainingGoal: '培养具备动物医学基础理论、知识和技能的高级专门人才。',
        mainCourses: ['动物解剖学', '动物生理学', '动物病理学', '兽医药理学', '兽医临床诊断学'],
        employmentDirections: ['动物医院', '畜牧兽医站', '动物检疫部门', '兽药生产企业', '科研院所'],
        furtherStudyDirections: ['基础兽医学', '预防兽医学', '临床兽医学'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 538,
            avgScore: 548,
            highScore: 568,
            provinceControlLine: 520,
            planCount: 40,
            actualCount: 42
          }
        }
      }
    ],
    overviewScores: {
      '2025': {
        scienceLow: 535,
        scienceAvg: 550,
        scienceHigh: 585,
        liberalArtsLow: 515,
        liberalArtsAvg: 530,
        liberalArtsHigh: 555
      }
    }
  },
  {
    id: 'southwest-petroleum-university',
    name: '西南石油大学',
    shortName: '西南石大',
    logo: '/images/southwest-petroleum-university/logo.png',
    type: 'SCIENCE',
    location: {
      district: '新都区',
      city: '成都市',
      province: '四川省'
    },
    address: '成都市新都区新都大道8号',
    phone: '028-83032000',
    website: 'https://www.swpu.edu.cn',
    description: '西南石油大学是一所以石油天然气学科为特色，理、工、管、经、文、法、艺协调发展的教学研究型大学。学校是国家“双一流”建设高校，以石油与天然气工程为优势学科。',
    foundingYear: 1958,
    department: '四川省教育厅',
    keyDisciplines: ['石油与天然气工程', '化学工程与技术', '机械工程', '地质资源与地质工程', '安全科学与工程'],
    images: [],
    majors: [
      {
        id: 'swpu-petroleum',
        name: '石油工程',
        code: '081502',
        department: '石油与天然气工程学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '石油工程专业是西南石油大学的王牌专业，国家重点学科。',
        trainingGoal: '培养具备工程基础理论和石油工程专业知识的高级工程技术人才。',
        mainCourses: ['油层物理', '钻井工程', '采油工程', '油藏工程', '油田化学'],
        employmentDirections: ['石油公司', '油田服务公司', '石油勘探开发研究院', '石油装备制造企业'],
        furtherStudyDirections: ['油气井工程', '油气田开发工程', '油气储运工程'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 562,
            avgScore: 575,
            highScore: 595,
            provinceControlLine: 520,
            planCount: 120,
            actualCount: 122
          }
        }
      },
      {
        id: 'swpu-chemical',
        name: '化学工程与工艺',
        code: '081301',
        department: '化学化工学院',
        duration: 4,
        degree: 'BACHELOR',
        subjectRequirement: 'SCIENCE',
        introduction: '化学工程与工艺专业培养从事化工生产、设计、研究与开发的高级工程技术人才。',
        trainingGoal: '培养具备化学工程与化学工艺知识的高级工程技术人才。',
        mainCourses: ['化工原理', '化工热力学', '反应工程', '化工工艺学', '化工设计'],
        employmentDirections: ['化工企业', '炼油企业', '石油化工企业', '科研院所', '化工设计研究院'],
        furtherStudyDirections: ['化学工程', '化学工艺', '应用化学'],
        relatedMajors: [],
        scores: {
          '2025': {
            subjectType: 'SCIENCE',
            batch: 'FIRST',
            lowScore: 558,
            avgScore: 568,
            highScore: 588,
            provinceControlLine: 520,
            planCount: 85,
            actualCount: 88
          }
        }
      }
    ],
    overviewScores: {
      '2025': {
        scienceLow: 555,
        scienceAvg: 570,
        scienceHigh: 605,
        liberalArtsLow: 535,
        liberalArtsAvg: 550,
        liberalArtsHigh: 575
      }
    }
  }
];
