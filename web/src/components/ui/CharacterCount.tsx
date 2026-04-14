type CharacterCountProps = {
  currentLength: number
  minLength?: number
  maxLength?: number
}

export default function CharacterCount({
  currentLength,
  minLength,
  maxLength,
}: CharacterCountProps) {
  const hasMinLength = typeof minLength === 'number'
  const hasMaxLength = typeof maxLength === 'number'

  if (!hasMinLength && !hasMaxLength) return null

  const isBelowMin = hasMinLength && currentLength < minLength
  const isAboveMax = hasMaxLength && currentLength > maxLength
  const isValid = !isBelowMin && !isAboveMax
  const counterLabel = hasMaxLength ? `${currentLength}/${maxLength}` : `${currentLength}/min. ${minLength}`

  return <p className={`text-right text-xs ${isValid ? 'text-emerald-400' : 'text-red-500'}`}>{counterLabel}</p>
}
