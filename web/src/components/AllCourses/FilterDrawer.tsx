import { useState } from 'react'

type Props<T extends string> = {
  selectedCategory: T
  setSelectedCategory: (category: T) => void
  categories: readonly T[]
}

export default function FilterDrawer<T extends string>({
  selectedCategory,
  setSelectedCategory,
  categories,
}: Props<T>) {
  const [isOpen, setIsOpen] = useState(false)

  function handleSelect(category: T) {
    setSelectedCategory(category)
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
            <p className="text-sm font-semibold text-white">Categorias</p>
            <p className="text-xs text-white/40">Escolha uma opção</p>
          </div>

          <div className="p-2">
            {categories.map((category) => {
              const isSelected = selectedCategory === category

              return (
                <button
                  key={category}
                  onClick={() => handleSelect(category)}
                  className={`flex w-full items-center justify-between rounded-xl px-3 py-2 text-left text-sm transition ${
                    isSelected
                      ? 'bg-cyan/20 text-cyan'
                      : 'text-white/80 hover:bg-white/10 hover:text-white'
                  }`}
                >
                  <span>{category}</span>
                  {isSelected && <span className="text-xs">✓</span>}
                </button>
              )
            })}
          </div>
        </div>
      )}
    </div>
  )
}
