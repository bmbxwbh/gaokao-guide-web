export type UniversityType = 
  | 'COMPREHENSIVE' 
  | 'SCIENCE' 
  | 'MEDICAL' 
  | 'NORMAL' 
  | 'FINANCE' 
  | 'OTHERS';

export type ImageCategory = 
  | 'SCENERY' 
  | 'DORMITORY' 
  | 'DINING_HALL' 
  | 'BUILDING';

export type DegreeType = 'BACHELOR' | 'MASTER' | 'DOCTOR';

export type RequirementType = 
  | 'MUST' 
  | 'ONE_OF' 
  | 'RECOMMENDED' 
  | 'NO_REQUIREMENT';

export type BatchType = 'FIRST' | 'SECOND' | 'BATCH_1' | 'BATCH_2';

export type SubjectType = 'SCIENCE' | 'LIBERAL_ARTS' | 'COMPREHENSIVE';

export type AdmissionProbability = 'SAFE' | 'SURE' | 'STABLE' | 'STRETCH' | 'RISKY';

export interface Location {
  district: string;
  city?: string;
  province?: string;
}

export interface CampusImage {
  id: string;
  path: string;
  category: ImageCategory;
  description?: string;
}

export interface YearOverviewScore {
  scienceLow?: number;
  scienceAvg?: number;
  scienceHigh?: number;
  liberalArtsLow?: number;
  liberalArtsAvg?: number;
  liberalArtsHigh?: number;
}

export type SubjectRequirement = string;

export interface MajorScore {
  batch: BatchType;
  subjectType: SubjectType;
  lowScore: number;
  avgScore: number;
  highScore: number;
  provinceControlLine: number;
  planCount?: number;
  actualCount?: number;
}

export interface Major {
  id: string;
  code: string;
  name: string;
  department: string;
  degree: DegreeType;
  duration: number;
  subjectRequirement?: SubjectRequirement;
  introduction: string;
  trainingGoal: string;
  mainCourses: string[];
  employmentDirections: string[];
  furtherStudyDirections: string[];
  scores: Record<string, MajorScore>;
  relatedMajors: string[];
  tags?: string[];
}

export interface University {
  id: string;
  name: string;
  shortName: string;
  logo: string;
  type: UniversityType;
  location: Location;
  address: string;
  phone: string;
  website: string;
  description: string;
  foundingYear: number;
  department: string;
  keyDisciplines: string[];
  images: CampusImage[];
  majors: Major[];
  overviewScores: Record<string, YearOverviewScore>;
}

// --- 收藏相关类型 ---
export interface Favorite {
  id: string;
  type: 'UNIVERSITY' | 'MAJOR';
  targetId: string;
  universityId?: string;
  timestamp: number;
}

// --- 对比相关类型 ---
export interface ComparisonItem {
  id: string;
  type: 'UNIVERSITY' | 'MAJOR';
  targetId: string;
  universityId?: string;
}

// --- 推荐相关类型 ---
export interface UserScores {
  totalScore: number;
  chinese: number;
  math: number;
  english: number;
  physics?: number;
  chemistry?: number;
  biology?: number;
  history?: number;
  geography?: number;
  politics?: number;
  subjectType: SubjectType;
}

export interface InterestCategory {
  id: string;
  name: string;
  icon: string;
}

export interface InterestTag {
  id: string;
  name: string;
  categoryId: string;
  relatedMajorTags: string[];
}

export interface RecommendationResult {
  universityId: string;
  universityName: string;
  universityShortName: string;
  majorId: string;
  majorName: string;
  majorCode: string;
  year: string;
  probability: AdmissionProbability;
  matchScore: number;
  avgScore: number;
  lowScore: number;
  subjectType: SubjectType;
  explanation?: string;
  aiSuggestion?: string;
}

export interface AIAnalysisRequest {
  userScores: UserScores;
  interests: string[];
  recommendations: RecommendationResult[];
}

export interface AIAnalysisResponse {
  success: boolean;
  analysis: string;
  suggestions: string[];
  personalizedAdvice: string;
}
