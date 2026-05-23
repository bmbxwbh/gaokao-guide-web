import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Card } from '../components/Card';
import { Input } from '../components/Input';
import { Chip } from '../components/Chip';
import { Button } from '../components/Button';
import { useAppContext } from '../context/AppContext';
import { INTEREST_CATEGORIES, INTEREST_TAGS } from '../data/interests';
import type { UserScores, SubjectType } from '../types';
import '../styles/pages.css';

export const RecommendationPage: React.FC = () => {
  const navigate = useNavigate();
  const { 
    userScores, 
    setUserScores, 
    selectedInterests, 
    setSelectedInterests,
    recommendations,
    setRecommendations,
    generateRecommendations
  } = useAppContext();

  const [activeTab, setActiveTab] = useState<'INPUT' | 'RESULTS'>(userScores && recommendations.length > 0 ? 'RESULTS' : 'INPUT');
  const [scoresForm, setScoresForm] = useState<Partial<UserScores>>({
    totalScore: userScores?.totalScore,
    chinese: userScores?.chinese,
    math: userScores?.math,
    english: userScores?.english,
    physics: userScores?.physics,
    chemistry: userScores?.chemistry,
    biology: userScores?.biology,
    history: userScores?.history,
    geography: userScores?.geography,
    politics: userScores?.politics,
    subjectType: userScores?.subjectType
  });

  const [loading, setLoading] = useState(false);

  const handleScoreChange = (field: keyof UserScores, value: string) => {
    setScoresForm(prev => ({
      ...prev,
      [field]: field === 'subjectType' ? value as SubjectType : Number(value) || undefined
    }));
  };

  const toggleInterest = (tagId: string) => {
    if (selectedInterests.includes(tagId)) {
      setSelectedInterests(selectedInterests.filter(id => id !== tagId));
    } else {
      setSelectedInterests([...selectedInterests, tagId]);
    }
  };

  const handleGenerate = () => {
    if (!scoresForm.totalScore || !scoresForm.subjectType) {
      alert('请填写总分和科类！');
      return;
    }

    setLoading(true);
    
    const validScores: UserScores = {
      totalScore: scoresForm.totalScore,
      chinese: scoresForm.chinese || 0,
      math: scoresForm.math || 0,
      english: scoresForm.english || 0,
      physics: scoresForm.physics,
      chemistry: scoresForm.chemistry,
      biology: scoresForm.biology,
      history: scoresForm.history,
      geography: scoresForm.geography,
      politics: scoresForm.politics,
      subjectType: scoresForm.subjectType
    };

    setUserScores(validScores);
    
    setTimeout(() => {
      const results = generateRecommendations(validScores, selectedInterests);
      setRecommendations(results);
      setLoading(false);
      setActiveTab('RESULTS');
    }, 500);
  };

  const callAIAnalysis = async () => {
    alert('AI 分析功能：预留接口，可接入 OpenAI / 文心一言 / 通义千问等 API');
  };

  const getProbabilityColor = (prob: string) => {
    switch (prob) {
      case 'SAFE': return '#10b981';
      case 'SURE': return '#34d399';
      case 'STABLE': return '#fbbf24';
      case 'STRETCH': return '#fb923c';
      case 'RISKY': return '#ef4444';
      default: return '#6b7280';
    }
  };

  const getProbabilityText = (prob: string) => {
    switch (prob) {
      case 'SAFE': return '稳妥';
      case 'SURE': return '稳妥';
      case 'STABLE': return '推荐';
      case 'STRETCH': return '冲刺';
      case 'RISKY': return '风险';
      default: return '未知';
    }
  };

  const tagsByCategory = INTEREST_CATEGORIES.map(cat => ({
    category: cat,
    tags: INTEREST_TAGS.filter(tag => tag.categoryId === cat.id)
  }));

  return (
    <div className="page-container">
      <div className="page-header">
        <div className="back-button" onClick={() => navigate('/scores')}>
          <svg width="24" height="24" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
          </svg>
          返回
        </div>
        <h1>智能志愿推荐</h1>
      </div>

      <div className="tabs">
        <div 
          className={`tab ${activeTab === 'INPUT' ? 'tab-active' : ''}`} 
          onClick={() => setActiveTab('INPUT')}
        >
          信息填写
        </div>
        <div 
          className={`tab ${activeTab === 'RESULTS' ? 'tab-active' : ''}`} 
          onClick={() => recommendations.length > 0 && setActiveTab('RESULTS')}
          style={{ opacity: recommendations.length > 0 ? 1 : 0.5 }}
        >
          推荐结果 {recommendations.length > 0 && `(${recommendations.length})`}
        </div>
      </div>

      {activeTab === 'INPUT' && (
        <div className="recommendation-input">
          <Card className="card-no-padding">
            <div className="card-header">
              <h3>📊 分数填写</h3>
            </div>
            <div className="card-content">
              <div className="score-inputs">
                <div className="score-input-group">
                  <label>总分 *</label>
                  <Input 
                    type="number" 
                    value={scoresForm.totalScore || ''} 
                    onChange={(e) => handleScoreChange('totalScore', e.target.value)} 
                    placeholder="例如：600"
                  />
                </div>
                
                <div className="score-input-group">
                  <label>科类 *</label>
                  <select
                    value={scoresForm.subjectType || ''}
                    onChange={(e) => handleScoreChange('subjectType', e.target.value)}
                    className="form-select"
                  >
                    <option value="">请选择</option>
                    <option value="SCIENCE">理科</option>
                    <option value="LIBERAL_ARTS">文科</option>
                  </select>
                </div>
                
                <div className="score-input-row">
                  <div className="score-input-group">
                    <label>语文</label>
                    <Input 
                      type="number" 
                      value={scoresForm.chinese || ''} 
                      onChange={(e) => handleScoreChange('chinese', e.target.value)} 
                      placeholder="100-150"
                    />
                  </div>
                  <div className="score-input-group">
                    <label>数学</label>
                    <Input 
                      type="number" 
                      value={scoresForm.math || ''} 
                      onChange={(e) => handleScoreChange('math', e.target.value)} 
                      placeholder="100-150"
                    />
                  </div>
                  <div className="score-input-group">
                    <label>英语</label>
                    <Input 
                      type="number" 
                      value={scoresForm.english || ''} 
                      onChange={(e) => handleScoreChange('english', e.target.value)} 
                      placeholder="100-150"
                    />
                  </div>
                </div>

                <div className="subjects-toggle">
                  <span style={{ color: 'var(--text-secondary)', marginRight: '1rem' }}>选考科目：</span>
                  <Chip 
                    active={!!scoresForm.physics} 
                    onClick={() => handleScoreChange('physics', scoresForm.physics ? '' : '100')}
                  >
                    物理
                  </Chip>
                  <Chip 
                    active={!!scoresForm.chemistry} 
                    onClick={() => handleScoreChange('chemistry', scoresForm.chemistry ? '' : '100')}
                  >
                    化学
                  </Chip>
                  <Chip 
                    active={!!scoresForm.biology} 
                    onClick={() => handleScoreChange('biology', scoresForm.biology ? '' : '100')}
                  >
                    生物
                  </Chip>
                  <Chip 
                    active={!!scoresForm.history} 
                    onClick={() => handleScoreChange('history', scoresForm.history ? '' : '100')}
                  >
                    历史
                  </Chip>
                  <Chip 
                    active={!!scoresForm.geography} 
                    onClick={() => handleScoreChange('geography', scoresForm.geography ? '' : '100')}
                  >
                    地理
                  </Chip>
                  <Chip 
                    active={!!scoresForm.politics} 
                    onClick={() => handleScoreChange('politics', scoresForm.politics ? '' : '100')}
                  >
                    政治
                  </Chip>
                </div>
              </div>
            </div>
          </Card>

          <Card className="card-no-padding" style={{ marginTop: '1.5rem' }}>
            <div className="card-header">
              <h3>🎯 兴趣方向 ({selectedInterests.length} 个)</h3>
              <p style={{ margin: 0, color: 'var(--text-secondary)', fontSize: '0.875rem' }}>
                选择你感兴趣的领域，帮助我们更精准地推荐
              </p>
            </div>
            <div className="card-content">
              <div className="interests-section">
                {tagsByCategory.map(({ category, tags }) => (
                  <div key={category.id} className="interest-category">
                    <h4 className="interest-category-title">
                      {category.icon} {category.name}
                    </h4>
                    <div className="interest-tags">
                      {tags.map(tag => (
                        <Chip
                          key={tag.id}
                          active={selectedInterests.includes(tag.id)}
                          onClick={() => toggleInterest(tag.id)}
                          variant={selectedInterests.includes(tag.id) ? 'primary' : 'secondary'}
                        >
                          {tag.name}
                        </Chip>
                      ))}
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </Card>

          <div style={{ marginTop: '2rem', textAlign: 'center' }}>
            <Button size="large" onClick={handleGenerate} disabled={loading}>
              {loading ? '生成中...' : '✨ 生成推荐'}
            </Button>
          </div>
        </div>
      )}

      {activeTab === 'RESULTS' && recommendations.length > 0 && (
        <div className="recommendation-results">
          <Card className="card-no-padding" style={{ marginBottom: '1.5rem' }}>
            <div className="card-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <div>
                <h3>📋 推荐结果</h3>
                <p style={{ margin: 0, color: 'var(--text-secondary)', fontSize: '0.875rem' }}>
                  基于你的分数 {userScores?.totalScore} 分（{userScores?.subjectType === 'SCIENCE' ? '理科' : '文科'}）
                </p>
              </div>
              <Button size="sm" onClick={callAIAnalysis}>
                🤖 AI 深度分析
              </Button>
            </div>
            <div className="card-content">
              <div className="results-list">
                {recommendations.map((result, index) => (
                  <Link
                    key={`${result.universityId}-${result.majorId}`}
                    to={`/university/${result.universityId}/major/${result.majorId}`}
                    className="result-item"
                  >
                    <div className="result-rank">#{index + 1}</div>
                    <div className="result-content">
                      <div className="result-header">
                        <div className="result-university">
                          {result.universityShortName}
                        </div>
                        <div 
                          className="result-probability"
                          style={{ 
                            backgroundColor: `${getProbabilityColor(result.probability)}20`,
                            color: getProbabilityColor(result.probability)
                          }}
                        >
                          {getProbabilityText(result.probability)}
                        </div>
                      </div>
                      <div className="result-major">{result.majorName}</div>
                      <div className="result-meta">
                        <span>专业代码：{result.majorCode}</span>
                        <span>去年平均：{result.avgScore}分</span>
                        <span>去年最低：{result.lowScore}分</span>
                      </div>
                      <div className="result-explanation">
                        {result.explanation}
                      </div>
                      <div className="match-score-bar">
                        <div 
                          className="match-score-fill"
                          style={{ 
                            width: `${result.matchScore}%`,
                            background: `linear-gradient(90deg, #10b981 0%, #34d399 100%)`
                          }}
                        />
                        <span className="match-score-text">匹配度 {Math.round(result.matchScore)}%</span>
                      </div>
                    </div>
                  </Link>
                ))}
              </div>
            </div>
          </Card>

          <div className="ai-section">
            <Card className="card-no-padding">
              <div className="card-header">
                <h3>🤖 AI 分析区域</h3>
                <p style={{ margin: 0, color: 'var(--text-secondary)', fontSize: '0.875rem' }}>
                  这里可以接入大模型 API，为您提供个性化的志愿填报建议
                </p>
              </div>
              <div className="card-content">
                <div style={{ textAlign: 'center', padding: '2rem', color: 'var(--text-secondary)' }}>
                  <div style={{ fontSize: '3rem', marginBottom: '1rem' }}>🔮</div>
                  <p>点击上方「AI 深度分析」按钮获取个性化建议</p>
                  <p style={{ fontSize: '0.875rem', marginTop: '0.5rem' }}>
                    接口位置：src/pages/RecommendationPage.tsx 中的 callAIAnalysis 函数
                  </p>
                </div>
              </div>
            </Card>
          </div>
        </div>
      )}
    </div>
  );
};
