import { ChevronRight } from 'lucide-react'
import { Fragment } from 'react'
import { Link, useLocation } from 'react-router-dom'

type Crumb = {
  label: string
  path: string
}

function formatLabel(segment: string) {
  return decodeURIComponent(segment)
    .replace(/[-_]+/g, ' ')
    .replace(/\b\w/g, (char) => char.toUpperCase())
}

function shouldSkipSegment(segments: string[], index: number) {
  const current = segments[index]
  const previous = segments[index - 1]
  const previousTwo = segments[index - 2]

  return (
    current === 'user' ||
    previous === 'editar-curso' ||
    previous === 'cursos' ||
    previousTwo === 'cursos'
  )
}

function buildLabel(segment: string, segments: string[], index: number) {
  if (segment === 'categorias') return 'Todos os cursos'
  if (segment === 'criar-curso') return 'Criar curso'
  if (segment === 'editar-curso') return 'Editar curso'
  if (segment === 'social') return 'Perfil publico'
  if (segment === 'conquistas') return 'Conquistas'
  if (segment === 'configuracoes') return 'Configurações'
  if (segment === 'login') return 'Entrar'
  if (segment === 'cadastro') return 'Cadastrar'

  if (segment === 'cursos') {
    return index > 0 && segments[index - 1] === 'user' ? 'Meus cursos' : 'Assistir curso'
  }

  return formatLabel(segment)
}

export default function Breadcrumbs() {
  const location = useLocation()
  const pathname = location.pathname.replace(/\/+$/, '') || '/'

  if (pathname === '/' || pathname === '/home') {
    return null
  }

  const segments = pathname.split('/').filter(Boolean)
  const isWatchRoute = segments.length >= 3 && segments[0] === 'cursos'

  if (isWatchRoute) {
    const watchCrumbs: Crumb[] = [
      { label: 'Meus cursos', path: '/user/cursos' },
      { label: 'Assistir aula', path: pathname },
    ]

    return (
      <nav className="border-b border-white/10 bg-[#10052b]">
        <div className="max-w-8xl mx-auto flex w-full flex-wrap items-center gap-2 py-3 pr-4 pl-7 text-sm sm:pr-6 sm:pl-9 lg:pr-8 lg:pl-11">
          <Link to="/" className="hover:text-cyan text-white/70 transition">
            Início
          </Link>

          {watchCrumbs.map((crumb, index) => {
            const isLast = index === watchCrumbs.length - 1

            return (
              <Fragment key={crumb.path}>
                <ChevronRight size={14} className="text-white/40" />
                {isLast ? (
                  <span className="font-medium text-white">{crumb.label}</span>
                ) : (
                  <Link to={crumb.path} className="hover:text-cyan text-white/70 transition">
                    {crumb.label}
                  </Link>
                )}
              </Fragment>
            )
          })}
        </div>
      </nav>
    )
  }

  const crumbs = segments.reduce<Crumb[]>((acc, segment, index) => {
    const path = `/${segments.slice(0, index + 1).join('/')}`

    if (shouldSkipSegment(segments, index)) {
      return acc
    }

    acc.push({
      label: buildLabel(segment, segments, index),
      path,
    })

    return acc
  }, [])

  return (
    <nav className="border-b border-white/10 bg-[#10052b]">
      <div className="max-w-8xl mx-auto flex w-full flex-wrap items-center gap-2 py-3 pr-4 pl-7 text-sm sm:pr-6 sm:pl-9 lg:pr-8 lg:pl-11">
        <Link to="/" className="hover:text-cyan text-white/70 transition">
          Início
        </Link>

        {crumbs.map((crumb, index) => {
          const isLast = index === crumbs.length - 1

          return (
            <Fragment key={crumb.path}>
              <ChevronRight size={14} className="text-white/40" />
              {isLast ? (
                <span className="font-medium text-white">{crumb.label}</span>
              ) : (
                <Link to={crumb.path} className="hover:text-cyan text-white/70 transition">
                  {crumb.label}
                </Link>
              )}
            </Fragment>
          )
        })}
      </div>
    </nav>
  )
}
