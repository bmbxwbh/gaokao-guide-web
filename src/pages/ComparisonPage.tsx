import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Card } from '../components/Card';
import { Button } from '../components/Button';
import { useAppContext } from '../context/AppContext';
import { universities } from '../data/universities';
import '../styles/pages.css';

export const ComparisonPage: React.FC = () => {
  const navigate = useNavigate();
  const { comparisonList, removeFromComparison, clearComparison } = useAppContext();

  const getUniversityById = (id: string) => universities.find(u => u.id === id);

  const getMajorById = (universityId: string, majorId: string) => {
    const university = getUniversityById(universityId);
    return university?.majors.find(m => m.id === majorId);
  };

  const itemsToCompare = comparisonList.map(item => {
    if (item.type === 'UNIVERSITY') {
      const university = getUniversityById(item.targetId);
      if (!university) return null;
      return {
        id: item.id,
        type: 'UNIVERSITY' as const,
        university
      };
    } else {
      if (!item.universityId) return null;
      const university = getUniversityById(item.universityId);
      const major = getMajorById(item.universityId, item.targetId);
      if (!university || !major) return null;
      return {
        id: item.id,
        type: 'MAJOR' as const,
        university,
        major
      };
    }
  }).filter(Boolean);

  if (itemsToCompare.length === 0) {
    return (
      <div className="page-container">
        <div className="page-header">
          <div className="back-button" onClick={() => navigate('/')}>
            <svg width="24" height="24" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
            </svg>
            返回
          </div>
          <h1>对比分析</h1>
        </div>

        <div className="empty-state">
          <div className="empty-icon">📊</div>
          <h3>暂无可对比项</h3>
          <p>在学校或专业详情页添加对比项吧</p>
          <div style={{ display: 'flex', gap: '1rem', marginTop: '1rem' }}>
            <Link to="/universities">
              <Button>查看学校</Button>
            </Link>
            <Link to="/scores">
              <Button>查看专业</Button>
            </Link>
          </div>
        </div>
      </div>
    );
  }

  const areAllUniversities = itemsToCompare.every(item => item?.type === 'UNIVERSITY');
  const areAllMajors = itemsToCompare.every(item => item?.type === 'MAJOR');

  return (
    <div className="page-container">
      <div className="page-header" style={{ justifyContent: 'space-between' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
          <div className="back-button" onClick={() => navigate('/')}>
            <svg width="24" height="24" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
            </svg>
            返回
          </div>
          <h1>对比分析</h1>
        </div>
        <Button size="sm" variant="secondary" onClick={clearComparison}>
          清空对比
        </Button>
      </div>

      {!areAllUniversities && !areAllMajors && (
        <div className="comparison-warning">
          ⚠️ 建议对比同类型项目（学校与学校比，专业与专业比）
        </div>
      )}

      <div className="comparison-grid">
        <div className="comparison-column comparison-labels">
          <div className="comparison-cell comparison-header">
            <span>对比项</span>
          </div>
          <div className="comparison-cell">名称</div>
          <div className="comparison-cell">基本信息</div>
          <div className="comparison-cell">分数情况</div>
        </div>

        {itemsToCompare.map(item => (
          <div key={item?.id} className="comparison-column">
            <div className="comparison-cell comparison-header">
              <button 
                className="remove-btn"
                onClick={() => removeFromComparison(item!.id)}
                title="移除"
              >
                <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
              {item?.type === 'UNIVERSITY' ? '🏫 学校' : '📚 专业'}
            </div>

            <div className="comparison-cell">
              {item?.type === 'UNIVERSITY' ? (
                <Link to={`/university/${item.university.id}`} className="comparison-link">
                  {item.university.name}
                </Link>
              ) : (
                <Link to={`/university/${item!.university.id}/major/${item!.major.id}`} className="comparison-link">
                  {item!.university.shortName} - {item!.major.name}
                </Link>
              )}
            </div>

            <div className="comparison-cell">
              {item?.type === 'UNIVERSITY' ? (
                <div className="comparison-details">
                  <p>📍 {item.university.location.district}</p>
                  <p>🎓 {item.university.type}</p>
                  <p>📅 建校：{item.university.foundingYear}</p>
                </div>
              ) : (
                <div className="comparison-details">
                  <p>🏫 {item!.university.name}</p>
                  <p>🏢 {item!.major.department}</p>
                  <p>📝 代码：{item!.major.code}</p>
                </div>
              )}
            </div>

            <div className="comparison-cell">
              {item?.type === 'UNIVERSITY' ? (
                <div className="comparison-details">
                  {Object.entries(item.university.overviewScores).map(([year, scores]) => (
                    <div key={year} style={{ marginBottom: '0.5rem' }}>
                      <strong>{year}年</strong>
                      {scores.scienceAvg && <p>理科平均：{scores.scienceAvg}</p>}
                      {scores.liberalArtsAvg && <p>文科平均：{scores.liberalArtsAvg}</p>}
                    </div>
                  ))}
                </div>
              ) : (
                <div className="comparison-details">
                  {Object.entries(item!.major.scores).map(([year, score]) => (
                    <div key={year} style={{ marginBottom: '0.5rem' }}>
                      <strong>{year}年</strong>
                      <p>平均：{score.avgScore} / 最低：{score.lowScore}</p>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        ))}
      </div>

      <Card style={{ marginTop: '2rem' }}>
        <div className="card-header">
          <h3>💡 提示</h3>
        </div>
        <div className="card-content">
          <ul style={{ margin: 0, paddingLeft: '1.25rem', color: 'var(--text-secondary)' }}>
            <li>支持同时对比 2-4 个项目</li>
            <li>建议对比同类型项目以便更好地分析</li>
            <li>可以分别对比学校或专业</li>
          </ul>
        </div>
      </Card>
    </div>
  );
};
