import { categoryOptions } from '@/constants/courseOptions'

function normalizeText(value: string) {
  return value
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .toLowerCase()
    .trim()
}

function toSlug(value: string) {
  return normalizeText(value).replace(/[^a-z0-9\s-]/g, '').replace(/[\s_]+/g, '-')
}

const searchableCategories = categoryOptions.map((category) => {
  const slug = toSlug(category.label)
  const normalizedLabel = normalizeText(category.label)
  const normalizedValue = normalizeText(category.value)
  const normalizedSlug = normalizeText(slug)
  const tokens = Array.from(
    new Set(
      `${normalizedLabel} ${normalizedSlug} ${normalizedValue}`
        .split(/[\s_-]+/g)
        .filter(Boolean)
    )
  )

  return {
    slug,
    normalizedLabel,
    normalizedValue,
    normalizedSlug,
    tokens,
  }
})

export function getCategorySlugFromSearchTerm(term: string) {
  const normalizedTerm = normalizeText(term)

  if (!normalizedTerm) {
    return undefined
  }

  const matches = searchableCategories.filter((category) => {
    if (
      category.normalizedValue === normalizedTerm ||
      category.normalizedSlug === normalizedTerm ||
      category.normalizedLabel === normalizedTerm
    ) {
      return true
    }

    if (normalizedTerm.length < 3) {
      return false
    }

    return (
      category.normalizedLabel.startsWith(normalizedTerm) ||
      category.normalizedSlug.startsWith(normalizedTerm) ||
      category.normalizedValue.startsWith(normalizedTerm) ||
      category.tokens.some(
        (token) => token.startsWith(normalizedTerm) || normalizedTerm.startsWith(token)
      )
    )
  })

  if (matches.length !== 1) {
    return undefined
  }

  return matches[0].slug
}
