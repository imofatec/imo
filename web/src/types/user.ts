export type User = {
  id: string
  name: string
  email: string
  isConfirmed: boolean
  profilePicturePath: string | null
  birthDate: string | null
  availableTimePerDay: string | null
  academicDegree: string | null
  experienceLevel: string | null
  categoriesOfInterest: string[] | null
}
