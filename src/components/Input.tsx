import React from 'react';
import '../styles/components.css';

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  leftIcon?: React.ReactNode;
  className?: string;
}

export const Input: React.FC<InputProps> = ({
  leftIcon,
  className = '',
  ...props
}) => {
  const inputClass = `input ${className}`.trim();
  
  if (leftIcon) {
    return (
      <div className="search-input">
        <span className="search-icon">{leftIcon}</span>
        <input className={inputClass} {...props} />
      </div>
    );
  }
  
  return <input className={inputClass} {...props} />;
};
