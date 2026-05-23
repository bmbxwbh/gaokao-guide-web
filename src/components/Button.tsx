import React from 'react';
import '../styles/components.css';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'ghost';
  size?: 'sm' | 'md' | 'lg' | 'large';
  children: React.ReactNode;
}

export const Button: React.FC<ButtonProps> = ({
  variant = 'primary',
  size = 'md',
  children,
  className = '',
  ...props
}) => {
  const btnClass = `btn btn-${variant} ${className}`.trim();
  
  return (
    <button 
      className={btnClass}
      {...props}
    >
      {children}
    </button>
  );
};
