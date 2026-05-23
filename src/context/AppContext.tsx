import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import type { Favorite, ComparisonItem, UserScores, RecommendationResult } from '../types';
import { universities } from '../data/universities';

interface AppContextType {
  favorites: Favorite[];
  isFavorite: (type: 'UNIVERSITY' | 'MAJOR', targetId: string, universityId?: string) => boolean;
  toggleFavorite: (type: 'UNIVERSITY' | 'MAJOR', targetId: string, universityId?: string) => void;
  comparisonList: ComparisonItem[];
  addToComparison: (item: ComparisonItem) => void;
  removeFromComparison: (id: string) => void;
  isInComparison: (type: 'UNIVERSITY' | 'MAJOR', targetId: string) => boolean;
  clearComparison: () => void;
  userScores: UserScores | null;
  setUserScores: (scores: UserScores | null) => void;
  selectedInterests: string[];
  setSelectedInterests: (interests: string[]) => void;
  recommendations: RecommendationResult[];
  setRecommendations: (recommendations: RecommendationResult[]) => void;
  generateRecommendations: (userScores: UserScores, interests: string[]) => RecommendationResult[];
}

const AppContext = createContext<AppContextType | undefined>(undefined);

const FAVORITES_KEY = 'gaokao_favorites';
const COMPARISON_KEY = 'gaokao_comparison';
const USER_SCORES_KEY = 'gaokao_user_scores';
const INTERESTS_KEY = 'gaokao_interests';

// 安全的 localStorage 操作
const getLocalStorageItem = (key: string): string | null => {
  if (typeof window === 'undefined') return null;
  try {
    return localStorage.getItem(key);
  } catch {
    return null;
  }
};

const setLocalStorageItem = (key: string, value: string) => {
  if (typeof window === 'undefined') return;
  try {
    localStorage.setItem(key, value);
  } catch {}
};

const removeLocalStorageItem = (key: string) => {
  if (typeof window === 'undefined') return;
  try {
    localStorage.removeItem(key);
  } catch {}
};

export const AppProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [favorites, setFavorites] = useState<Favorite[]>([]);
  const [comparisonList, setComparisonList] = useState<ComparisonItem[]>([]);
  const [userScores, setUserScores] = useState<UserScores | null>(null);
  const [selectedInterests, setSelectedInterests] = useState<string[]>([]);
  const [recommendations, setRecommendations] = useState<RecommendationResult[]>([]);
  const [isLoaded, setIsLoaded] = useState(false);

  // 客户端加载 localStorage 数据
  useEffect(() => {
    const savedFavorites = getLocalStorageItem(FAVORITES_KEY);
    const savedComparison = getLocalStorageItem(COMPARISON_KEY);
    const savedUserScores = getLocalStorageItem(USER_SCORES_KEY);
    const savedInterests = getLocalStorageItem(INTERESTS_KEY);

    if (savedFavorites) setFavorites(JSON.parse(savedFavorites));
    if (savedComparison) setComparisonList(JSON.parse(savedComparison));
    if (savedUserScores) setUserScores(JSON.parse(savedUserScores));
    if (savedInterests) setSelectedInterests(JSON.parse(savedInterests));
    
    setIsLoaded(true);
  }, []);

  // 保存到 localStorage
  useEffect(() => {
    if (!isLoaded) return;
    setLocalStorageItem(FAVORITES_KEY, JSON.stringify(favorites));
  }, [favorites, isLoaded]);

  useEffect(() => {
    if (!isLoaded) return;
    setLocalStorageItem(COMPARISON_KEY, JSON.stringify(comparisonList));
  }, [comparisonList, isLoaded]);

  useEffect(() => {
    if (!isLoaded) return;
    if (userScores) {
      setLocalStorageItem(USER_SCORES_KEY, JSON.stringify(userScores));
    } else {
      removeLocalStorageItem(USER_SCORES_KEY);
    }
  }, [userScores, isLoaded]);

  useEffect(() => {
    if (!isLoaded) return;
    setLocalStorageItem(INTERESTS_KEY, JSON.stringify(selectedInterests));
  }, [selectedInterests, isLoaded]);

  const isFavorite = (type: 'UNIVERSITY' | 'MAJOR', targetId: string, universityId?: string) => {
    return favorites.some(fav => 
      fav.type === type && 
      fav.targetId === targetId && 
      (universityId ? fav.universityId === universityId : true)
    );
  };

  const toggleFavorite = (type: 'UNIVERSITY' | 'MAJOR', targetId: string, universityId?: string) => {
    setFavorites(prev => {
      const existingIndex = prev.findIndex(fav => 
        fav.type === type && 
        fav.targetId === targetId && 
        (universityId ? fav.universityId === universityId : true)
      );
      
      if (existingIndex >= 0) {
        return prev.filter((_, i) => i !== existingIndex);
      } else {
        return [...prev, {
          id: Date.now().toString(),
          type,
          targetId,
          universityId,
          timestamp: Date.now()
        }];
      }
    });
  };

  const addToComparison = (item: ComparisonItem) => {
    if (comparisonList.length >= 4) return;
    if (!isInComparison(item.type, item.targetId)) {
      setComparisonList(prev => [...prev, item]);
    }
  };

  const removeFromComparison = (id: string) => {
    setComparisonList(prev => prev.filter(item => item.id !== id));
  };

  const isInComparison = (type: 'UNIVERSITY' | 'MAJOR', targetId: string) => {
    return comparisonList.some(item => item.type === type && item.targetId === targetId);
  };

  const clearComparison = () => {
    setComparisonList([]);
  };

  const generateRecommendations = (userScores: UserScores, interests: string[]): RecommendationResult[] => {
    const results: RecommendationResult[] = [];
    const targetScore = userScores.totalScore;
    
    universities.forEach(university => {
      university.majors.forEach(major => {
        const latestYear = Object.keys(major.scores).sort().reverse()[0];
        if (!latestYear) return;
        
        const score = major.scores[latestYear];
        if (score.subjectType !== userScores.subjectType) return;
        
        const scoreDiff = targetScore - score.avgScore;
        
        let probability: 'SAFE' | 'SURE' | 'STABLE' | 'STRETCH' | 'RISKY';
        let explanation = '';
        
        if (scoreDiff >= 30) {
          probability = 'SAFE';
          explanation = '你的分数远高于去年平均分，录取概率极高';
        } else if (scoreDiff >= 15) {
          probability = 'SURE';
          explanation = '你的分数高于去年平均分，录取较稳妥';
        } else if (scoreDiff >= 0) {
          probability = 'STABLE';
          explanation = '你的分数接近去年平均分，录取机会较大';
        } else if (scoreDiff >= -15) {
          probability = 'STRETCH';
          explanation = '你的分数略低于去年平均分，可以尝试冲刺';
        } else {
          probability = 'RISKY';
          explanation = '你的分数低于去年平均分较多，风险较大';
        }
        
        let matchScore = 100 - Math.abs(scoreDiff) * 0.5;
        
        const hasMatchingTags = major.tags && major.tags.some(tag => 
          interests.some(interest => tag.includes(interest))
        );
        if (hasMatchingTags) {
          matchScore += 15;
        }
        
        if (matchScore > 0) {
          results.push({
            universityId: university.id,
            universityName: university.name,
            universityShortName: university.shortName,
            majorId: major.id,
            majorName: major.name,
            majorCode: major.code,
            year: latestYear,
            probability,
            matchScore: Math.min(matchScore, 100),
            avgScore: score.avgScore,
            lowScore: score.lowScore,
            subjectType: score.subjectType,
            explanation,
            aiSuggestion: undefined
          });
        }
      });
    });
    
    return results.sort((a, b) => b.matchScore - a.matchScore).slice(0, 50);
  };

  return (
    <AppContext.Provider
      value={{
        favorites,
        isFavorite,
        toggleFavorite,
        comparisonList,
        addToComparison,
        removeFromComparison,
        isInComparison,
        clearComparison,
        userScores,
        setUserScores,
        selectedInterests,
        setSelectedInterests,
        recommendations,
        setRecommendations,
        generateRecommendations
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

export const useAppContext = () => {
  const context = useContext(AppContext);
  if (!context) {
    throw new Error('useAppContext must be used within an AppProvider');
  }
  return context;
};
