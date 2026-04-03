import { useState } from 'react'
import type { Category } from '@/types/category'
import { Link } from 'react-router-dom'

type Props = {
  categories: Category[]
  selectedCategory: string | null
}

export default function FilterDrawer({ categories, selectedCategory }: Props) {
  const [isOpen, setIsOpen] = useState(false)

  return (
    <div className="relative ml-auto">
      <button
        onClick={() => setIsOpen((prev) => !prev)}
        className="border-cyan/30 bg-cyan/10 text-cyan hover:bg-cyan/20 rounded-xl border px-4 py-2 text-sm font-medium transition"
      >
        Filtrar
      </button>

      {isOpen && (
        <div className="absolute right-0 z-50 mt-3 w-56 overflow-hidden rounded-2xl border border-white/10 bg-[#14082f] shadow-2xl">
          <div className="border-b border-white/10 px-4 py-3">
            <p className="text-sm font-semibold text-white">Categorias</p>
            <p className="text-xs text-white/40">Escolha uma opção</p>
          </div>

          <div className="p-2">
            <Link
              to="/allcourses"
              className={`flex w-full items-center justify-between rounded-xl px-3 py-2 text-left text-sm transition ${
                selectedCategory === null
                  ? 'bg-cyan/20 text-cyan'
                  : 'text-white/80 hover:bg-white/10 hover:text-white'
              }`}
            >
              <span>Todos os cursos</span>
              {selectedCategory === null && <span className="text-xs">✓</span>}
            </Link>

            {categories.map((category) => (
              <Link
                to={`/allcourses/${category.slug}`}
                key={category.slug}
                className={`flex w-full items-center justify-between rounded-xl px-3 py-2 text-left text-sm transition ${
                  selectedCategory === category.slug
                    ? 'bg-cyan/20 text-cyan'
                    : 'text-white/80 hover:bg-white/10 hover:text-white'
                }`}
              >
                <span>{category.name}</span>
                {selectedCategory === category.slug && <span className="text-xs">✓</span>}
              </Link>
            ))}
          </div>
        </div>
      )}
    </div>
  )
}
