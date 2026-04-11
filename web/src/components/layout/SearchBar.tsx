import { Search } from 'lucide-react'
import { useEffect, useState, type FormEvent } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom'

export default function SearchBar() {
  const navigate = useNavigate()
  const [searchParams] = useSearchParams()
  const [searchTerm, setSearchTerm] = useState('')

  useEffect(() => {
    // eslint-disable-next-line
    setSearchTerm(searchParams.get('name') ?? '')
  }, [searchParams])

  function handleSearch(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()

    const trimmedSearch = searchTerm.trim()

    navigate(
      trimmedSearch ? `/categorias?name=${encodeURIComponent(trimmedSearch)}` : '/categorias'
    )
  }

  return (
    <form onSubmit={handleSearch} className="relative flex w-196 items-center">
      <input
        type="search"
        value={searchTerm}
        onChange={(event) => setSearchTerm(event.target.value)}
        placeholder="Buscar curso"
        className="w-full rounded-2xl border border-white/10 bg-white/10 py-3 pr-14 pl-5 text-sm text-white outline-none transition placeholder:text-white/40 focus:border-cyan/50 focus:bg-white/15"
      />

      <button
        type="submit"
        className="text-cyan absolute right-2 flex h-10 w-10 items-center justify-center rounded-xl transition hover:bg-white/10"
        aria-label="Buscar cursos"
      >
        <Search size={18} />
      </button>
    </form>
  )
}
