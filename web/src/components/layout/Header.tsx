import LogoIMO from '@/assets/LogoIMO.svg'

import { Link } from 'react-router-dom'

export default function Header() {
  return (
    <header className="flex flex-col">
      <div className="bg-dark-purple flex h-12.5 items-center justify-around">
        <Link to={'/'}>
          <img src={LogoIMO} className="cursor-pointer" />
        </Link>

        <div className="relative flex w-196 items-center">
          <p>dropdownsearch</p>
        </div>
        <p>dropdown</p>
      </div>

      <div className="bg-cyan mb-3 flex min-h-10 flex-wrap items-center justify-center gap-9 text-black">
        <p>Header Item 1</p>
        <p>Header Item 2</p>
        <p>Header Item 3</p>
      </div>
    </header>
  )
}
