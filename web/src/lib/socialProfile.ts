export function getSocialProfileOptionLabel(
  value: string | null | undefined,
  options: { label: string; value: string }[],
  fallback: string
) {
  if (!value) return fallback

  return options.find((option) => option.value === value)?.label ?? value
}

export function formatSocialProfileBirthDate(value: string | null) {
  if (!value) return 'Nao informado'

  const parsedDate = new Date(value)

  if (Number.isNaN(parsedDate.getTime())) {
    return value
  }

  return new Intl.DateTimeFormat('pt-BR', {
    dateStyle: 'long',
  }).format(parsedDate)
}

export function getSocialProfileInitials(name: string) {
  const parts = name.trim().split(/\s+/).filter(Boolean).slice(0, 2)

  if (!parts.length) return 'U'

  return parts.map((part) => part[0]?.toUpperCase() ?? '').join('')
}
