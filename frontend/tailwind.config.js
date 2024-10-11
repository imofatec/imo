/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: ['class'],
  content: [
    './pages/**/*.{js,jsx}',
    './components/**/*.{js,jsx}',
    './app/**/*.{js,jsx}',
    './src/**/*.{js,jsx}',
  ],
  prefix: '',
  theme: {
    container: {
      center: 'true',
      padding: '2rem',
      screens: {
        '2xl': '1400px',
      },
    },
    extend: {
      maxWidth: {
        thumb: '560px',
      },
      maxHeight: {
        thumb: '315px',
      },
      colors: {
        'custom-blue': '#111827',
        'custom-light-blue': '#1E64E5',
        'custom-gray': '#2D2F31',
        'custom-border-gray': '#374151',
        'custom-text-gray': '#9CA3AF',
        'custom-header-dark-purple': '#0E0025',
        'custom-header-cyan': '#2DEFD8',
        'custom-search-dark': '#1F192A',
        'custom-footer-dark': '#00110E',
        'custom-dark-blue': '#05000E',
        'custom-dataScience-index': '#25A9E1',
        'custom-redes-index': '#25E1CB',
        'custom-gestao-index': '#E19925'
      },
      backgroundColor: {
        'custom-dark-background': '#0c0424',
      },
      keyframes: {
        'accordion-down': {
          from: {
            height: '0',
          },
          to: {
            height: 'var(--radix-accordion-content-height)',
          },
        },
        'accordion-up': {
          from: {
            height: 'var(--radix-accordion-content-height)',
          },
          to: {
            height: '0',
          },
        },
        'accordion-down': {
          from: {
            height: '0',
          },
          to: {
            height: 'var(--radix-accordion-content-height)',
          },
        },
        'accordion-up': {
          from: {
            height: 'var(--radix-accordion-content-height)',
          },
          to: {
            height: '0',
          },
        },
      },
      animation: {
        'accordion-down': 'accordion-down 0.2s ease-out',
        'accordion-up': 'accordion-up 0.2s ease-out',
        'accordion-down': 'accordion-down 0.2s ease-out',
        'accordion-up': 'accordion-up 0.2s ease-out',
      },
    },
  },
  plugins: [require('tailwindcss-animate'),(require('tailwind-scrollbar'))],
}
