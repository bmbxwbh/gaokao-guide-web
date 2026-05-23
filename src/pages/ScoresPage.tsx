import React, { useState, useMemo } from 'react';
import { Link } from 'react-router-dom';
import { Card } from '../components/Card';
import { Input } from '../components/Input';
import { Chip } from '../components/Chip';
import { Button } from '../components/Button';
import { 
  useAllMajorScores, 
  useUniversitiesList 
} from '../hooks/useUniversity';
import { 
  getSubjectTypeName, 
  getBatchName 
} from '../utils/formatters';
import '../styles/pages.css';

export const ScoresPage: React.FC = () => {
  const allScores = useAllMajorScores();
  const universities = useUniversitiesList();

  // 筛选状态
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedUniversityId, setSelectedUniversityId] = useState<string>('ALL');
  const [selectedSubjectType, setSelectedSubjectType] = useState<string>('ALL');
  const [scoreRangeMin, setScoreRangeMin] = useState<string>('');
  const [scoreRangeMax, setScoreRangeMax] = useState<string>('');

  // 获取年份列表
  const years = useMemo(() => {
    const uniqueYears = new Set<string>();
    allScores.forEach(s => uniqueYears.add(s.year));
    return Array.from(uniqueYears).sort((a, b) => b.localeCompare(a));
  }, [allScores]);

  const [selectedYear, setSelectedYear] = useState<string>(years[0] || '2025');

  // 筛选逻辑
  const filteredScores = useMemo(() => {
    return allScores.filter(item => {
      // 年份筛选
      if (item.year !== selectedYear) return false;

      // 搜索筛选（学校名称或专业名称）
      const matchesSearch = 
        searchQuery === '' ||
        item.universityName.toLowerCase().includes(searchQuery.toLowerCase()) ||
        item.majorName.toLowerCase().includes(searchQuery.toLowerCase()) ||
        item.majorCode.toLowerCase().includes(searchQuery.toLowerCase());

      // 学校筛选
      const matchesUniversity = 
        selectedUniversityId === 'ALL' || 
        item.universityId === selectedUniversityId;

      // 科类筛选
      const matchesSubject = 
        selectedSubjectType === 'ALL' || 
        item.score.subjectType === selectedSubjectType;

      // 分数段筛选
      const minScore = scoreRangeMin ? parseInt(scoreRangeMin, 10) : 0;
      const maxScore = scoreRangeMax ? parseInt(scoreRangeMax, 10) : Infinity;
      const avgScore = item.score.avgScore;
      const matchesScore = avgScore >= minScore && avgScore <= maxScore;

      return matchesSearch && matchesUniversity && matchesSubject && matchesScore;
    }).sort((a, b) => b.score.avgScore - a.score.avgScore);
  }, [
    allScores, 
    selectedYear, 
    searchQuery, 
    selectedUniversityId, 
    selectedSubjectType, 
    scoreRangeMin, 
    scoreRangeMax
  ]);

  const resetFilters = () => {
    setSearchQuery('');
    setSelectedUniversityId('ALL');
    setSelectedSubjectType('ALL');
    setScoreRangeMin('');
    setScoreRangeMax('');
  };

  return (
    <div className="page-scores">
      <div className="container">
        <header className="page-header">
          <div className="back-btn-container">
            <Link to="/" className="back-btn">
              <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
              </svg>
              返回首页
            </Link>
          </div>
          <h1>专业分数汇总</h1>
          <p className="subtitle">查看所有学校专业的历年录取分数线</p>
        </header>

        {/* 筛选区域 */}
        <Card className="filter-card">
          <div className="filter-controls">
            {/* 年份筛选 */}
            <div className="filter-group">
              <label className="filter-label">年份</label>
              <div className="chip-group">
                {years.map(year => (
                  <Chip
                    key={year}
                    active={selectedYear === year}
                    onClick={() => setSelectedYear(year)}
                  >
                    {year}年
                  </Chip>
                ))}
              </div>
            </div>

            {/* 搜索 */}
            <div className="filter-group">
              <label className="filter-label">搜索</label>
              <Input
                placeholder="搜索学校、专业名称或代码..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
            </div>

            {/* 学校筛选 */}
            <div className="filter-group">
              <label className="filter-label">学校</label>
              <div className="select-wrapper">
                <select
                  value={selectedUniversityId}
                  onChange={(e) => setSelectedUniversityId(e.target.value)}
                  className="filter-select"
                >
                  <option value="ALL">全部学校</option>
                  {universities.map(univ => (
                    <option key={univ.id} value={univ.id}>
                      {univ.name}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            {/* 科类筛选 */}
            <div className="filter-group">
              <label className="filter-label">科类</label>
              <div className="chip-group">
                <Chip
                  active={selectedSubjectType === 'ALL'}
                  onClick={() => setSelectedSubjectType('ALL')}
                >
                  全部
                </Chip>
                <Chip
                  active={selectedSubjectType === 'SCIENCE'}
                  onClick={() => setSelectedSubjectType('SCIENCE')}
                >
                  理科
                </Chip>
                <Chip
                  active={selectedSubjectType === 'LIBERAL_ARTS'}
                  onClick={() => setSelectedSubjectType('LIBERAL_ARTS')}
                >
                  文科
                </Chip>
              </div>
            </div>

            {/* 分数段筛选 */}
            <div className="filter-group">
              <label className="filter-label">分数段（平均分）</label>
              <div className="score-range-inputs">
                <Input
                  placeholder="最低分"
                  value={scoreRangeMin}
                  onChange={(e) => setScoreRangeMin(e.target.value)}
                  type="number"
                />
                <span className="range-separator">~</span>
                <Input
                  placeholder="最高分"
                  value={scoreRangeMax}
                  onChange={(e) => setScoreRangeMax(e.target.value)}
                  type="number"
                />
              </div>
            </div>

            {/* 重置按钮 */}
            <div className="filter-group filter-actions">
              <Button variant="secondary" onClick={resetFilters}>
                重置筛选
              </Button>
            </div>
          </div>
        </Card>

        {/* 结果统计 */}
        <div className="result-summary">
          共找到 <span className="result-count">{filteredScores.length}</span> 条数据
        </div>

        {/* 分数列表 */}
        {filteredScores.length === 0 ? (
          <div className="empty-state">
            <p>没有找到符合条件的专业分数数据</p>
            <p className="text-sm text-secondary">请尝试调整筛选条件</p>
          </div>
        ) : (
          <div className="scores-list">
            {filteredScores.map((item, index) => (
              <Card 
                key={item.id} 
                hoverable 
                className="score-card-item"
                style={{ animationDelay: `${index * 50}ms` }}
              >
                <Link
                  to={`/university/${item.universityId}/major/${item.majorId}`}
                  style={{ textDecoration: 'none', color: 'inherit', display: 'block' }}
                >
                  <div className="score-card-header">
                    <div className="university-info">
                      <h3 className="major-name">{item.majorName}</h3>
                      <p className="university-name">
                        {item.universityName} · {item.department}
                      </p>
                      <p className="major-code">专业代码：{item.majorCode}</p>
                    </div>
                    <div className="score-badge">
                      <span className="score-value">{item.score.avgScore}</span>
                      <span className="score-label">平均分</span>
                    </div>
                  </div>
                  <div className="score-details">
                    <div className="score-detail-item">
                      <span className="detail-label">科类/批次</span>
                      <span className="detail-value">
                        {getSubjectTypeName(item.score.subjectType)} / {getBatchName(item.score.batch)}
                      </span>
                    </div>
                    <div className="score-detail-item">
                      <span className="detail-label">最低/最高</span>
                      <span className="detail-value">
                        {item.score.lowScore} ~ {item.score.highScore}
                      </span>
                    </div>
                    <div className="score-detail-item">
                      <span className="detail-label">省控线</span>
                      <span className="detail-value">{item.score.provinceControlLine}</span>
                    </div>
                    {item.score.planCount && (
                      <div className="score-detail-item">
                        <span className="detail-label">计划招生</span>
                        <span className="detail-value">{item.score.planCount}人</span>
                      </div>
                    )}
                  </div>
                </Link>
              </Card>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};
