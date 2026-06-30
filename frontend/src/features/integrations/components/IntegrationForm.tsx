import { useForm, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import styled from 'styled-components'
import { Input, ErrorText, Field } from '@/components/Input'
import { Label } from '@/components/Label'
import { Select } from '@/components/Select'
import { Button } from '@/components/Button'
import type { IntegrationRequest, IntegrationResponse } from '../hooks/useIntegrations'

const ALL_SYSTEMS = [
  { value: 'FASTCHICKENS_HR', label: 'FastChickensHR' },
  { value: 'STATE_OF_MICHIGAN', label: 'State of Michigan' },
  { value: 'WORKDAY', label: 'Workday' },
  { value: 'ADP', label: 'ADP' },
  { value: 'BENEFITFOCUS', label: 'Benefitfocus' },
]

const integrationSchema = z.object({
  name: z.string().min(1, 'Name is required'),
  fromSystem: z.string().min(1, 'From system is required'),
  toSystem: z.string().min(1, 'To system is required'),
})

type IntegrationFormValues = z.infer<typeof integrationSchema>

interface IntegrationFormProps {
  defaultValues?: Partial<IntegrationFormValues>
  organizationId: string
  createdByUserId: string
  onSubmit: (data: IntegrationRequest) => Promise<void>
  onCancel: () => void
  submitLabel?: string
}

const FormGrid = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.spacing[5]};
  max-width: 36rem;
`

const Actions = styled.div`
  display: flex;
  gap: ${({ theme }) => theme.spacing[3]};
  padding-top: ${({ theme }) => theme.spacing[2]};
`

const DisabledField = styled.div`
  padding: ${({ theme }) => `${theme.spacing[2]} ${theme.spacing[3]}`};
  border: 1px solid ${({ theme }) => theme.colors.gray[200]};
  border-radius: ${({ theme }) => theme.borderRadius.md};
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.colors.gray[500]};
  background-color: ${({ theme }) => theme.colors.gray[50]};
`

export function IntegrationForm({
  defaultValues,
  organizationId,
  createdByUserId,
  onSubmit,
  onCancel,
  submitLabel = 'Save',
}: IntegrationFormProps) {
  const {
    register,
    handleSubmit,
    control,
    watch,
    setError,
    formState: { errors, isSubmitting },
  } = useForm<IntegrationFormValues>({
    resolver: zodResolver(integrationSchema),
    defaultValues: {
      name: defaultValues?.name ?? '',
      fromSystem: defaultValues?.fromSystem ?? '',
      toSystem: defaultValues?.toSystem ?? '',
    },
  })

  const fromSystem = watch('fromSystem')

  const toSystemOptions = ALL_SYSTEMS.filter((s) => s.value !== fromSystem)
  const fromSystemOptions = ALL_SYSTEMS.filter((s) => {
    const toSystem = watch('toSystem')
    return s.value !== toSystem
  })

  const handleFormSubmit = async (values: IntegrationFormValues) => {
    try {
      await onSubmit({
        ...values,
        organizationId,
        createdByUserId,
        format: 'X12_834',
      })
    } catch {
      setError('root', { message: 'Failed to save. Please try again.' })
    }
  }

  return (
    <form onSubmit={handleSubmit(handleFormSubmit)}>
      <FormGrid>
        <Field>
          <Label htmlFor="name">Name</Label>
          <Input id="name" $hasError={!!errors.name} {...register('name')} />
          {errors.name && <ErrorText>{errors.name.message}</ErrorText>}
        </Field>

        <Field>
          <Label>From System</Label>
          <Controller
            name="fromSystem"
            control={control}
            render={({ field }) => (
              <Select
                value={field.value}
                onValueChange={field.onChange}
                options={fromSystemOptions}
                placeholder="Select source system…"
                $hasError={!!errors.fromSystem}
              />
            )}
          />
          {errors.fromSystem && <ErrorText>{errors.fromSystem.message}</ErrorText>}
        </Field>

        <Field>
          <Label>To System</Label>
          <Controller
            name="toSystem"
            control={control}
            render={({ field }) => (
              <Select
                value={field.value}
                onValueChange={field.onChange}
                options={toSystemOptions}
                placeholder="Select destination system…"
                $hasError={!!errors.toSystem}
              />
            )}
          />
          {errors.toSystem && <ErrorText>{errors.toSystem.message}</ErrorText>}
        </Field>

        <Field>
          <Label>Format</Label>
          <DisabledField>X12 834 — ANSI X12 834 Benefit Enrollment and Maintenance</DisabledField>
        </Field>

        {errors.root && <ErrorText>{errors.root.message}</ErrorText>}

        <Actions>
          <Button type="submit" disabled={isSubmitting}>
            {isSubmitting ? 'Saving…' : submitLabel}
          </Button>
          <Button type="button" $variant="secondary" onClick={onCancel}>
            Cancel
          </Button>
        </Actions>
      </FormGrid>
    </form>
  )
}

// Re-export for use in edit page pre-population
export type { IntegrationFormValues }
export function integrationResponseToFormValues(r: IntegrationResponse): IntegrationFormValues {
  return { name: r.name, fromSystem: r.fromSystem, toSystem: r.toSystem }
}
