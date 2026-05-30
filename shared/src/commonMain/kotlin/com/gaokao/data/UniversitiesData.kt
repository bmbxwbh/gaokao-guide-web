package com.gaokao.data

import com.gaokao.model.*

object UniversitiesData {
    val universities = listOf(
        University(
            id = "xihua-university",
            name = "西华大学",
            shortName = "西华",
            logo = "/images/xihua-university/logo.png",
            type = UniversityType.SCIENCE,
            location = Location(district = "郫都区", city = "成都市", province = "四川省"),
            address = "成都市郫都区红光镇广场路二段189号",
            phone = "028-87720000",
            website = "https://www.xhu.edu.cn",
            description = "西华大学是四川省属重点综合性大学，是四川省首批一流学科建设高校、四川省首批工程硕博士校企联合培养改革试点高校。现有郫都、宜宾、彭州、人南、易三仓（泰国）五个校区，占地面积近4000亩，全日制在校学生3.6万余人，其中硕士研究生近5000人。学校是国家知识产权试点高校、全国易班共建示范高校，具有面向港澳台地区招生资格、面向全国推荐优秀应届本科毕业生免试攻读硕士研究生资格。学校始建于1960年，时名四川农业机械学院，是为贯彻毛泽东主席关于\"农业的根本出路在于机械化\"的重要指示，原国家农机部在全国布点并建成的5所农业机械本科院校之一。1978年被四川省确定为六所重点高校之一，1982年成为全国首批学士学位授予单位，1983年更名为四川工业学院，1985年开始招收硕士研究生，1990年获批硕士学位授予权。2003年与成都师范高等专科学校合并组建西华大学，2008年四川经济管理学院整体并入西华大学。学校推进学科专业一体化发展，历经65年本科教育和40年研究生教育的办学积淀，拥有工、理、管、法、经、艺、文、教、农、交叉学科10个学科门类，3个四川省\"双一流\"贡嘎计划建设学科、8个省级重点学科，43个硕士学位授权点。学校设有21个学院，75个本科招生专业，获批16个国家一流专业建设点、4个国家级特色专业、1个国家级综合改革试点专业，9个专业通过工程教育专业认证。校训：求是、明德、卓越办学精神：知难而进、自强不息",
            foundingYear = 1960,
            department = "四川省教育厅",
            keyDisciplines = listOf("流体机械及工程", "食品科学与工程", "计算机科学与技术", "机械工程", "车辆工程"),
            images = listOf(
                CampusImage("xihua-university-img-1", "/images/西华大学/校园风景/01_夜阑人静.jpg", ImageCategory.SCENERY),
                CampusImage("xihua-university-img-2", "/images/西华大学/校园风景/02_雏声苑夜景.jpg", ImageCategory.SCENERY),
                CampusImage("xihua-university-img-3", "/images/西华大学/校园风景/03_卓越广场.jpg", ImageCategory.SCENERY),
                CampusImage("xihua-university-img-4", "/images/西华大学/校园风景/04_西华卓越广场全景.jpg", ImageCategory.SCENERY),
                CampusImage("xihua-university-img-5", "/images/西华大学/校园风景/05_西华夕阳.jpg", ImageCategory.SCENERY)
            ),
            majors = listOf(
                Major(
                    id = "xihua-university-1",
                    code = "",
                    name = "电气工程及其自动化",
                    department = "",
                    degree = DegreeType.BACHELOR,
                    duration = 4,
                    subjectRequirement = SubjectType.PHYSICS,
                    introduction = "",
                    trainingGoal = "",
                    mainCourses = emptyList(),
                    employmentDirections = emptyList(),
                    furtherStudyDirections = emptyList(),
                    relatedMajors = emptyList(),
                    scores = mapOf(
                        "2025" to MajorScore(
                            subjectType = SubjectType.PHYSICS,
                            batch = BatchType.FIRST,
                            lowScore = 590,
                            avgScore = 595,
                            highScore = 590,
                            provinceControlLine = 518,
                            planCount = 50,
                            actualCount = 52
                        )
                    )
                ),
                Major(
                    id = "xihua-university-2",
                    code = "",
                    name = "计算机科学与技术",
                    department = "",
                    degree = DegreeType.BACHELOR,
                    duration = 4,
                    subjectRequirement = SubjectType.PHYSICS,
                    introduction = "",
                    trainingGoal = "",
                    mainCourses = emptyList(),
                    employmentDirections = emptyList(),
                    furtherStudyDirections = emptyList(),
                    relatedMajors = emptyList(),
                    scores = mapOf(
                        "2025" to MajorScore(
                            subjectType = SubjectType.PHYSICS,
                            batch = BatchType.FIRST,
                            lowScore = 569,
                            avgScore = 572,
                            highScore = 579,
                            provinceControlLine = 518,
                            planCount = 50,
                            actualCount = 52
                        )
                    )
                ),
                Major(
                    id = "xihua-university-40",
                    code = "",
                    name = "思想政治教育",
                    department = "",
                    degree = DegreeType.BACHELOR,
                    duration = 4,
                    subjectRequirement = SubjectType.HISTORY,
                    introduction = "",
                    trainingGoal = "",
                    mainCourses = emptyList(),
                    employmentDirections = emptyList(),
                    furtherStudyDirections = emptyList(),
                    relatedMajors = emptyList(),
                    scores = mapOf(
                        "2025" to MajorScore(
                            subjectType = SubjectType.HISTORY,
                            batch = BatchType.FIRST,
                            lowScore = 573,
                            avgScore = 576,
                            highScore = 583,
                            provinceControlLine = 533,
                            planCount = 50,
                            actualCount = 52
                        )
                    )
                )
            ),
            overviewScores = mapOf(
                "2025" to YearOverviewScore(
                    physicsLow = 501,
                    physicsAvg = 525,
                    physicsHigh = 590,
                    historyLow = 548,
                    historyAvg = 558,
                    historyHigh = 582
                )
            )
        ),
        University(
            id = "chengdu-university",
            name = "成都大学",
            shortName = "成大",
            logo = "/images/chengdu-university/logo.png",
            type = UniversityType.COMPREHENSIVE,
            location = Location(district = "龙泉驿区", city = "成都市", province = "四川省"),
            address = "成都市龙泉驿区十陵镇上街1号",
            phone = "028-84616000",
            website = "https://www.cdu.edu.cn",
            description = "成都大学创建于1978年，是改革开放后首批地方城市主办的全日制普通本科院校。学校实行省市共建、以市为主的办学体制，是四川省、成都市重点建设大学，是第31届世界大学生夏季运动会运动员村承办大学，FISU银级认证\"健康校园\"。拥有百年历史的三级甲等综合附属医院和国家级科研平台四川抗菌素工业研究所。",
            foundingYear = 1978,
            department = "成都市人民政府",
            keyDisciplines = listOf("生物医学工程", "食品科学与工程", "旅游管理", "建筑学", "设计学"),
            images = listOf(
                CampusImage("chengdu-university-img-1", "/images/成都大学/校园风景/01_校园风光1.jpg", ImageCategory.SCENERY),
                CampusImage("chengdu-university-img-2", "/images/成都大学/校园风景/02_校园风光2.jpg", ImageCategory.SCENERY)
            ),
            majors = listOf(
                Major(
                    id = "chengdu-university-43",
                    code = "",
                    name = "计算机科学与技术",
                    department = "",
                    degree = DegreeType.BACHELOR,
                    duration = 4,
                    subjectRequirement = SubjectType.PHYSICS,
                    introduction = "",
                    trainingGoal = "",
                    mainCourses = emptyList(),
                    employmentDirections = emptyList(),
                    furtherStudyDirections = emptyList(),
                    relatedMajors = emptyList(),
                    scores = mapOf(
                        "2025" to MajorScore(
                            subjectType = SubjectType.PHYSICS,
                            batch = BatchType.FIRST,
                            lowScore = 584,
                            avgScore = 587,
                            highScore = 609,
                            provinceControlLine = 518,
                            planCount = 50,
                            actualCount = 52
                        )
                    )
                )
            ),
            overviewScores = mapOf(
                "2025" to YearOverviewScore(
                    physicsLow = 520,
                    physicsAvg = 540,
                    physicsHigh = 587,
                    historyLow = 550,
                    historyAvg = 565,
                    historyHigh = 590
                )
            )
        )
    )

    fun getUniversityById(id: String): University? = universities.find { it.id == id }

    fun getMajorById(universityId: String, majorId: String): Major? {
        val university = getUniversityById(universityId) ?: return null
        return university.majors.find { it.id == majorId }
    }
}
