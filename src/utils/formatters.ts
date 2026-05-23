import type { UniversityType, DegreeType, BatchType, SubjectType } from '../types';

export const getUniversityTypeName = (type: UniversityType): string => {
  const names: Record<UniversityType, string> = {
    COMPREHENSIVE: '综合类',
    SCIENCE: '理工类',
    MEDICAL: '医药类',
    NORMAL: '师范类',
    FINANCE: '财经类',
    OTHERS: '其他'
  };
  return names[type] || '其他';
};

export const getDegreeName = (degree: DegreeType): string => {
  const names: Record<DegreeType, string> = {
    BACHELOR: '学士',
    MASTER: '硕士',
    DOCTOR: '博士'
  };
  return names[degree] || degree;
};

export const getSubjectTypeName = (type: SubjectType | string): string => {
  const names: Record<string, string> = {
    SCIENCE: '理科',
    LIBERAL_ARTS: '文科',
    COMPREHENSIVE: '综合改革'
  };
  return names[type] || type;
};

export const getBatchName = (batch: BatchType | string): string => {
  const names: Record<string, string> = {
    BATCH_1: '本科一批',
    BATCH_2: '本科二批',
    FIRST: '本科一批',
    SECOND: '本科二批'
  };
  return names[batch] || batch;
};

export const getRequirementText = (req: string | any): string => {
  if (!req) return '无要求';
  if (typeof req === 'string') return req;
  const types: Record<string, string> = {
    MUST: '必须选考',
    ONE_OF: '选考其一',
    RECOMMENDED: '推荐选考',
    NO_REQUIREMENT: '无要求'
  };
  const subjects = req.subjects?.join('、') || '';
  return `${types[req.type] || req.type}${subjects ? `: ${subjects}` : ''}`;
};
