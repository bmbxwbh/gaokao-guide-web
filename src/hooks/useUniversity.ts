import { useMemo } from 'react';
import { universities } from '../data/universities';
import type { UniversityType, MajorScore } from '../types';

export interface AggregatedMajorScore {
  id: string;
  universityId: string;
  universityName: string;
  universityShortName: string;
  majorId: string;
  majorName: string;
  majorCode: string;
  department: string;
  year: string;
  score: MajorScore;
}

export const useUniversity = (id: string | undefined) => {
  return useMemo(() => {
    if (!id) return null;
    return universities.find(u => u.id === id) || null;
  }, [id]);
};

export const useFilteredUniversities = (
  searchQuery: string,
  selectedType: UniversityType | 'ALL'
) => {
  return useMemo(() => {
    return universities.filter(univ => {
      const matchesSearch = 
        univ.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        univ.shortName.toLowerCase().includes(searchQuery.toLowerCase());
      
      const matchesType = selectedType === 'ALL' || univ.type === selectedType;
      
      return matchesSearch && matchesType;
    });
  }, [searchQuery, selectedType]);
};

export const useUniversityTypes = () => {
  return useMemo(() => {
    const types = new Set<UniversityType>(universities.map(u => u.type));
    return Array.from(types);
  }, []);
};

export const useAllMajorScores = () => {
  return useMemo(() => {
    const scores: AggregatedMajorScore[] = [];
    universities.forEach(univ => {
      univ.majors.forEach(major => {
        Object.entries(major.scores).forEach(([year, score]) => {
          scores.push({
            id: `${univ.id}-${major.id}-${year}`,
            universityId: univ.id,
            universityName: univ.name,
            universityShortName: univ.shortName,
            majorId: major.id,
            majorName: major.name,
            majorCode: major.code,
            department: major.department,
            year,
            score
          });
        });
      });
    });
    return scores;
  }, []);
};

export const useUniversitiesList = () => {
  return useMemo(() => universities, []);
};

export const useMajorsList = () => {
  return useMemo(() => {
    const majors: Array<{ id: string; name: string; universityId: string; universityName: string }> = [];
    universities.forEach(univ => {
      univ.majors.forEach(major => {
        majors.push({
          id: major.id,
          name: major.name,
          universityId: univ.id,
          universityName: univ.name
        });
      });
    });
    return majors;
  }, []);
};
