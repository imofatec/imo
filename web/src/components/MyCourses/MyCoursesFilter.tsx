import { useState } from 'react'

export const myCoursesFilters = [
  'TODOS',
  'FINALIZADOS',
  'EM_PROGRESSO',
  'CONTRIBUICAO',
] as const

export type MyCoursesFilter = (typeof myCoursesFilters)[number]

type FilterOption = {
  value: MyCoursesFilter
  label: string
}

const filterOptions: FilterOption[] = [
  { value: 'TODOS', label: 'Todos' },
  { value: 'FINALIZADOS', label: 'Finalizados' },
  { value: 'EM_PROGRESSO', label: 'Em progresso' },
  { value: 'CONTRIBUICAO', label: 'Contribuição' },
]

type Props = {
  selectedFilter: MyCoursesFilter
  onSelect: (filter: MyCoursesFilter) => void
}

export default function MyCoursesFilter({ selectedFilter, onSelect }: Props) {
  const [isOpen, setIsOpen] = useState(false)

  function handleSelect(filter: MyCoursesFilter) {
    onSelect(filter)
    setIsOpen(false)
  }

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
            <p className="text-sm font-semibold text-white">Meus cursos</p>
            <p className="text-xs text-white/40">Escolha uma opcao</p>
          </div>

          <div className="p-2">
            {filterOptions.map((option) => (
              <button
                key={option.value}
                onClick={() => handleSelect(option.value)}
                className={`flex w-full items-center justify-between rounded-xl px-3 py-2 text-left text-sm transition ${
                  selectedFilter === option.value
                    ? 'bg-cyan/20 text-cyan'
                    : 'text-white/80 hover:bg-white/10 hover:text-white'
                }`}
              >
                <span>{option.label}</span>
                {selectedFilter === option.value && <span className="text-xs">✓</span>}
              </button>
            ))}
          </div>
        </div>
      )}
    </div>
  )
}
