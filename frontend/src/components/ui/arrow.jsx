import React from 'react'

export function Arrow({ orientation, size }) {
  {
    /*
        Para utilizar essa seta (ou "triangulinho", como preferir) tem 2 configurações pré definidas:
         "orientation" que guia o sentido para o qual aponta
         "size" que dita o tamanho
    */
  }
  switch (orientation) {
    case 'down':
      orientation = '-rotate-90'
      break
    case 'up':
      orientation = 'rotate-90'
      break
    case 'right':
      orientation = 'rotate-180'
      break
    default:
      orientation = ''
      break
  }
  switch (size) {
    case 'sm':
      size = '1.2rem'
      break
    default:
      size = '2rem'
  }
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width={size}
      height={size}
      viewBox="0 0 24 24"
      className={orientation}
    >
      <path fill="currentColor" fillRule="evenodd" d="m16 5l-8 7l8 7z"></path>
    </svg>
  )
}
