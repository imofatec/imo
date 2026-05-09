import LogoIMO from '@/assets/LogoIMO.svg'
import { Github, Mail } from 'lucide-react'
import { Link } from 'react-router-dom'

export default function Footer() {
  const currentYear = new Date().getFullYear()

  return (
    <footer className="bg-dark-purple/50 border-t border-white/5 text-sm text-gray-500">
      <div className="mx-auto w-full max-w-7xl px-6 py-6 lg:px-10">
        <div className="flex flex-col items-center justify-between gap-4 md:flex-row">
          <div className="flex items-center gap-5">
            <Link to="/home" className="inline-flex items-center gap-3">
              <img src={LogoIMO} alt="IMO" className="h-5 w-auto" />
            </Link>
            <p> {currentYear} IMO.</p>
          </div>

          <div className="flex items-center gap-6">
            <a
              href="mailto:imo.fatec@gmail.com"
              className="flex items-center gap-2 transition-colors hover:text-white"
            >
              <Mail size={16} className="text-cyan" /> imo.fatec@gmail.com
            </a>

            <a
              href="https://github.com/imofatec/imo"
              target="_blank"
              rel="noreferrer"
              className="flex items-center gap-2 transition-colors hover:text-white"
            >
              <Github size={16} className="text-cyan" /> GitHub
            </a>
          </div>
        </div>
      </div>
    </footer>
  )
}