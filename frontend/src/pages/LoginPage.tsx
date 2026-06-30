import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useNavigate } from 'react-router-dom'
import styled from 'styled-components'
import { useAuth } from '@/hooks/useAuth'

const loginSchema = z.object({
  email: z.string().email('Enter a valid email'),
  password: z.string().min(1, 'Password is required'),
  organizationId: z.string().uuid('Enter a valid Organization ID'),
})

type LoginFormValues = z.infer<typeof loginSchema>

const Title = styled.h1`
  font-size: ${({ theme }) => theme.fontSize['2xl']};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  color: ${({ theme }) => theme.colors.gray[900]};
  margin: 0 0 ${({ theme }) => theme.spacing[6]};
  text-align: center;
`

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.spacing[4]};
`

const Field = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.spacing[1]};
`

const Label = styled.label`
  font-size: ${({ theme }) => theme.fontSize.sm};
  font-weight: ${({ theme }) => theme.fontWeight.medium};
  color: ${({ theme }) => theme.colors.gray[700]};
`

const Input = styled.input<{ $hasError?: boolean }>`
  padding: ${({ theme }) => `${theme.spacing[2]} ${theme.spacing[3]}`};
  border: 1px solid
    ${({ theme, $hasError }) => ($hasError ? theme.colors.red[500] : theme.colors.gray[300])};
  border-radius: ${({ theme }) => theme.borderRadius.md};
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.colors.gray[900]};
  outline: none;

  &:focus {
    border-color: ${({ theme }) => theme.colors.blue[500]};
    box-shadow: 0 0 0 3px ${({ theme }) => theme.colors.blue[100]};
  }
`

const ErrorText = styled.p`
  font-size: ${({ theme }) => theme.fontSize.xs};
  color: ${({ theme }) => theme.colors.red[600]};
  margin: 0;
`

const SubmitButton = styled.button`
  padding: ${({ theme }) => `${theme.spacing[2]} ${theme.spacing[4]}`};
  background-color: ${({ theme }) => theme.colors.blue[600]};
  color: ${({ theme }) => theme.colors.white};
  border: none;
  border-radius: ${({ theme }) => theme.borderRadius.md};
  font-size: ${({ theme }) => theme.fontSize.sm};
  font-weight: ${({ theme }) => theme.fontWeight.medium};
  cursor: pointer;
  transition: background-color 150ms ease;

  &:hover:not(:disabled) {
    background-color: ${({ theme }) => theme.colors.blue[700]};
  }

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
`

export function LoginPage() {
  const { login } = useAuth()
  const navigate = useNavigate()

  const {
    register,
    handleSubmit,
    setError,
    formState: { errors, isSubmitting },
  } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
  })

  const onSubmit = async (values: LoginFormValues) => {
    try {
      await login(values)
      navigate('/dashboard')
    } catch {
      setError('root', { message: 'Invalid email or password' })
    }
  }

  return (
    <>
      <Title>Sign in</Title>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Field>
          <Label htmlFor="email">Email</Label>
          <Input
            id="email"
            type="email"
            autoComplete="email"
            $hasError={!!errors.email}
            {...register('email')}
          />
          {errors.email && <ErrorText>{errors.email.message}</ErrorText>}
        </Field>
        <Field>
          <Label htmlFor="password">Password</Label>
          <Input
            id="password"
            type="password"
            autoComplete="current-password"
            $hasError={!!errors.password}
            {...register('password')}
          />
          {errors.password && <ErrorText>{errors.password.message}</ErrorText>}
        </Field>
        <Field>
          <Label htmlFor="organizationId">Organization ID</Label>
          <Input
            id="organizationId"
            type="text"
            placeholder="xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
            $hasError={!!errors.organizationId}
            {...register('organizationId')}
          />
          {errors.organizationId && <ErrorText>{errors.organizationId.message}</ErrorText>}
        </Field>
        {errors.root && <ErrorText>{errors.root.message}</ErrorText>}
        <SubmitButton type="submit" disabled={isSubmitting}>
          {isSubmitting ? 'Signing in…' : 'Sign in'}
        </SubmitButton>
      </Form>
    </>
  )
}
